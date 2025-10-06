let submitBtn = document.getElementById("submit-btn")
let errorMessage = document.querySelector(".error-message")
let registrationForm = document.getElementById("registrationForm")

registrationForm.addEventListener("submit", function (event){


    let login = document.getElementById("signup-login").value
    let password = document.getElementById("signup-password").value
    let passwordAgain = document.getElementById("signup-password-again").value
    let email = document.getElementById("signup-email").value

    if(login.length === 0 || password.length === 0
        || passwordAgain.length === 0 || email.length === 0){
        showError('Заповніть усі поля');
        event.preventDefault();
        return 1
    }

    if(login === "test"){
        showError(`Користувач ${login} вже існує`);
        event.preventDefault();
        return 1
    }

    if(login.length >= 20){
        showError('Занадто довгий логін');
        event.preventDefault();
        return 1
    }

    if(password !== passwordAgain){
        showError('Паролі не збігаються');
        event.preventDefault();
        return 1
    }

    if(email === "test@gmail.com"){
        showError('Пошта з такою адресою не знайдена');
        event.preventDefault();
        return 1
    }

    let user = {
        login: login,
        pass: password,
        email: email
    }

    errorMessage.style.display = 'none'

    alert(`Successful`)
    console.log(user)
})

function showError(message) {
    errorMessage.textContent = message;
    errorMessage.style.display = 'block';
}