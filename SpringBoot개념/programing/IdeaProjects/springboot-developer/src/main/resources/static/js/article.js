// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        function success() {
            alert('삭제가 완료되었습니다.');
            location.replace('/articles');
        }

        function fail() {
            alert('삭제 실패했습니다.');
            location.replace('/articles');
        }

        httpRequest('DELETE',`/api/articles/${id}`, null, success, fail);
    });
}

/*
    HTML 에서 delete-btn으로 설정한 엘리먼트를 찾아 그 엘리먼트에서 클릭 이벤트가 발생하면
    fetch() 메소드에 이어지는 then() 메소드는 fetch()가 잘 완료되면 연이어 실행되는 메소드

    alert() 메소드는 then() 메소드가 실행되는 시점에 웹 브라우저 화면으로 삭제가 완료되었음을 알리는 팝업

    location.replace() 메소드는 실행 시 사용자의 웹 브라우저 화면을 현재 주소를 기반해 옮겨준느 역할
*/

// 수정 기능
// 1) id가 modify-btn인 엘리먼트 조회
const modifyButton = document.getElementById("modify-btn");

if(modifyButton) {
    // 2) 클릭 이벤트가 감지되면 수정 API 요청
    modifyButton.addEventListener("click", event => {
        let params = new URLSearchParams(location.search);
        let id = params.get("id");

        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        })

        function success() {
            alert('수정 완료되었습니다.');
            location.replace(`/articles/${id}`);
        }

        function fail() {
            alert('수정 실패했습니다.');
            location.replace(`/articles/${id}`);
        }

        httpRequest('PUT',`/api/articles/${id}`, body, success, fail);
    });
}


/*
    id가 modify-btn 인 엘리먼트를 찾고 그 엘리먼트에서 클릭 이벤트가 발생하면 id 가 title, content 인
    엘리먼트 값을 가져와 fetch() 메소드를 통해 수정 API 로 /api/articles/ PUT 요청을 보냄

    요청을 보낼 때는 headers 에 요청 형식을 지정하고, body 에 HTML 에 입력한 데이터를 JSON 형식으로 바꿔 보냄

    요청이 완료되면 then() 메소드롷 마무리 작업
*/


//등록 기능
// 1) id가 create-btn인 엘리먼트

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    // 등록 버튼을 클릭하면 /api/articles로 요청을 보낸다
    createButton.addEventListener('click', event => {
        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });
        function success() {
            alert('등록 완료되었습니다.');
            location.replace('/articles');
        };
        function fail() {
            alert('등록 실패했습니다.');
            location.replace('/articles');
        };

        httpRequest('POST','/api/articles', body, success, fail)
    });
}


// 쿠키를 가져오는 함수
function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        var dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'),
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then(result => { // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
                    localStorage.setItem('access_token', result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch(error => fail());
        } else {
            return fail();
        }
    });
}