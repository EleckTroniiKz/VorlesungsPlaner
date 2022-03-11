const slideButtons = document.querySelector('.slideButtons');
const slideButtonList = slideButtons.children
const aside = document.querySelector('aside')
const slideBackground = document.querySelector('.slideBackground');
const slideLabel = document.querySelector('.slideLabel');
aside.style.background = "url(./images/Code-Stock.jpg)"
aside.style.backgroundSize = "cover";

const paragraphCar = document.querySelector('#content');

function showFirstSlide(){
    aside.style.background = "url(./images/Code-Stock.jpg)"
    aside.style.backgroundSize = "cover"
    slideLabel.innerText = "CREATE"
    //paragraphCar.innerText = "Kreieren Sie nach belieben neue Elemente"
}

function showSecondSlide(){
    aside.style.background = "url(./images/Bibliothek-stock.jpg)"
    aside.style.backgroundSize = "cover"
    slideLabel.innerText = "READ"
    //paragraphCar.innerText = "Lesen Sie die von Ihnen gebrauchten Daten aus!"
}

function showThirdSlide(){
    aside.style.background = "url(./images/books.jpg)"
    aside.style.backgroundSize = "cover"
    slideLabel.innerText = "UPDATE"
    //paragraphCar.innerText = "Bearbeiten Sie die von Ihnen erstellten Elemente"
}

function showFourthSlide(){
    aside.style.background = "url(./images/Laptop.jpg)"
    aside.style.backgroundSize = "cover"
    slideLabel.innerText = "DELETE"
    //paragraphCar.innerText = "Sie brauchen ein Element nicht mehr? Dann löschen Sie es!"
}

function slideButtonsClicked(btn) {

    if(btn.className !== "active"){
        for(let i = 0; i < slideButtonList.length; i++){
            if(btn === slideButtonList[i]){
                btn.className = "active"
                switch(i){
                    case 0:
                        showFirstSlide()
                        break;
                    case 1:
                        showSecondSlide()
                        break;
                    case 2:
                        showThirdSlide()
                        break;
                    case 3:
                        showFourthSlide()
                        break;
                }
            }
            else{
                slideButtonList[i].className = "inactive"
            }
        }
    }
}