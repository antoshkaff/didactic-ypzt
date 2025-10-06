
  document.addEventListener('DOMContentLoaded', () => {
    
    const questionLists = document.querySelectorAll('.question-list');

    questionLists.forEach(questionList => {
      questionList.addEventListener('click', () => {
        
        const questionInfo = questionList.nextElementSibling;

        if (questionInfo.hasAttribute('hidden')) {
          questionInfo.removeAttribute('hidden'); 
          questionList.querySelector('p').textContent = '−'; 
        } else {
          questionInfo.setAttribute('hidden', ''); 
          questionList.querySelector('p').textContent = '+'; 
        }
      });
    });
  });
