const loginForm = document.querySelector('#login');
const saveForm = document.querySelector('#register');
const loginBtn = loginForm.querySelector('#loginBtn');
const registerBtn = saveForm.querySelector('#registerBtn');

loginBtn.addEventListener('click', async () => {
    try {
        clearErrorMsg();
        const data = {
            email: loginForm.querySelector('.email').value,
            password: loginForm.querySelector('.passwd').value
        };

        const result = await fetch('user-service/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify(data),
        }).then(response =>{
            return response.json();
        });

        if(result.ok){
            console.log(result.results);
        } else {
            console.log (result.results);
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
        console.log("회원가입: "+data);
        console.log("회원가입: "+data.toString());

        const result = await fetch('user-service/api/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify(data),
        }).then(response =>{
            return response.json();
        });

        if(result.ok){
            console.log(result.results);
            alert("회원가입 성공");
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
