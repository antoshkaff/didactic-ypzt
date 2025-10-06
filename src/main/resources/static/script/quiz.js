document.addEventListener('DOMContentLoaded', () => {
    const finishButton = document.querySelector('.submit-element');
    const modal = document.querySelector('.container-modal-error');
    const closeButton = document.querySelector('.btn-modal');
    const questions = document.querySelectorAll('.question');
    const usernameInput = document.querySelector('.name-input');
    const errorList = document.querySelector('.error-que');

    finishButton.addEventListener('click', (event) => {
        event.preventDefault(); 
        let unanswered = [];
        let errors = [];

        questions.forEach((question, index) => {
            const radios = question.querySelectorAll('input[type="radio"]');
            const isAnswered = Array.from(radios).some(radio => radio.checked);

            if (!isAnswered) {
                unanswered.push(`Питання №${index + 1}`);
            }
        });

        if (unanswered.length > 0 || errors.length > 0) {
            modal.style.display = 'flex';
            document.body.classList.add('no-scroll'); 
            errorList.innerHTML = [...errors, ...unanswered.map(q => `<p>${q}</p>`)].join('');
        } else {
            document.querySelector('.main-container').submit();
        }
    });

    closeButton.addEventListener('click', () => {
        modal.style.display = 'none';
        document.body.classList.remove('no-scroll'); 
    });
});
