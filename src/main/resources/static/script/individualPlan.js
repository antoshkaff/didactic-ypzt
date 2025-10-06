document.addEventListener('DOMContentLoaded', () => {
  
  const level2Items = document.querySelectorAll('.level-2');

  level2Items.forEach((item) => {
    
    item.addEventListener('click', () => {
      
      const level3List = item.nextElementSibling;


      if (level3List && level3List.classList.contains('level-3')) {

        level3List.classList.toggle('open');
      }
    });
  });
});
