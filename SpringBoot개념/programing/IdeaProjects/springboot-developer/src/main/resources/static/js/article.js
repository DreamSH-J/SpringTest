//삭제 기능

const deleteButton = document.getElementById("delete-btn");

if (deleteButton) {
    deleteButton.addEventListener("click", event => {
        let id = document.getElementById("article-id").value;
        fetch(`/api/articles/${id}`, {
            method: "DELETE"
        })
            .then(() => {
                alert("삭제가 완료되었습니다.");
                location.replace("/articles");
            });
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

        fetch(`/api/articles/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById("title").value,
                content: document.getElementById("content").value
            })
        })
            .then(() => {
                alert("수정이 완료 되었습니다");
                location.replace(`/articles/${id}`);
            });
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

const createButton = document.getElementById("create-btn");

if(createButton) {
    // 2) 클릭 이벤트가 감지되면 생성 API 사용
    createButton.addEventListener("click", (event) => {
        fetch("api/articles", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById("title").value,
                content: document.getElementById("content").value,
            }),
        })
            .then(() => {
                alert("등록 완료되었습니다");
                location.replace("/articles");
            });
    });
}