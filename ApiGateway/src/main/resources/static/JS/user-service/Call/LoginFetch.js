const loginForm = document.querySelector('#login');
const saveForm = document.querySelector('#register');
const loginBtn = loginForm.querySelector('#loginBtn');
const registerBtn = saveForm.querySelector('#registerBtn');

loginBtn.addEventListener('click', async () => {
    console.log("LoginFetch 작동");
    try {
        clearErrorMsg();
        const data = {
            email: loginForm.querySelector('.email').value,
            passwd: loginForm.querySelector('.passwd').value
        };
        console.log("받아온 정보 : "+data.email+" "+data.passwd);
        const result = await fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify(data),
        });

        console.log("다음으로 진행합니다. 상태: "+result.status);

        if(result.status === 200){
            console.log("맵 화면으로 이동합니다.");
            window.location.href="/map-service/Map";
        } else {
            console.log ("실패!!! "+result.results);
            setErrorMessage(result.results, loginBtn);
        }

    } catch (e){
        console.log(e)
        return e;
    }

});

registerBtn.addEventListener('click', async () => {
    try{
        clearErrorMsg();
        const data= {
            email : saveForm.querySelector('.email').value,
            passwd : saveForm.querySelector('.passwd').value,
            name : saveForm.querySelector('.name').value,
            phoneNumber : saveForm.querySelector('.phoneNumber').value
        };

        const result = await fetch('/user-service/api/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify(data),
        })
        if(result.status===200){
            console.log(result.results);
            alert("회원가입 성공");
            const x = document.getElementById("login");
            const y = document.getElementById("register");
            const z = document.getElementById("btn");

            x.style.left = "50px";
            y.style.left = "450px";
            z.style.left = "0";

        } else {
            console.log("에러 작동");
            setErrorMessage(result.message,registerBtn);
        }

    } catch (e) {
        console.log(e)
        return e;
    }

});

function setErrorMessage(errorMsg, buttonEle){
    const errorLabel = document.createElement("label");
    errorLabel.setAttribute("for", "username");
    errorLabel.classList.add("error-label");

    errorLabel.textContent=errorMsg;

    buttonEle.insertAdjacentElement("afterend",errorLabel);

}

function clearErrorMsg(){
    if(document.querySelector(".error-label")){
        document.querySelector(".error-label").remove();
    }
}
