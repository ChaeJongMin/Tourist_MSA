const searchInput = document.querySelector('#inputLocation');
const autocompleteResults = document.querySelector('#autocompleteResults');
const searchParent=document.querySelector('#search');
autocompleteResults.style.display='none';

export function setSearchEvent(data){
    const autocompleteResults = document.querySelector('#autocompleteResults');
    searchInput.addEventListener('input', function() {
        const inputValue = searchInput.value;
        const touristNmArray=data;
        let matches=[];
        if(inputValue !== ''){
            matches = touristNmArray.filter((touristNm) => touristNm.includes(inputValue));
            autocompleteResults.style.display = 'block';
            showList(matches);
            if (matches.length>4) {
                autocompleteResults.style.overflowY = 'scroll';
            }
        } else {
            initSearch();
        }
    });
}

function showList(matches) {
    removeLiEle();

    searchParent.style.height = 45 + 'px';

    let height = 0;
    let ulHeight=0;

    for (let i = 0; i < matches.length; i++) {
        let li = document.createElement('li');
        li.textContent = matches[i];
        autocompleteResults.appendChild(li);
    }

    const liElements = autocompleteResults.querySelectorAll('li'); // li 요소들을 선택합니다.
    if(matches.length!==0){
        if (matches.length > 4) {
            ulHeight = 160;
        } else {
            const firstLi = autocompleteResults.querySelector('li:first-child');
            ulHeight = firstLi.clientHeight*liElements.length;
        }
        liClickEvent(liElements);
        addBorderShadow();
    }
    else {
        removeBorderShadow();
        autocompleteResults.style.display = 'none';
    }
    height = 65 + ulHeight;
    searchParent.style.height =  height+ 'px';
    autocompleteResults.style.height =  ulHeight+ 'px';
}
function liClickEvent(liElements){
    liElements.forEach(ele =>{
        ele.addEventListener('click',function(){
            //ul 삭제
            //input text로 이동
            searchInput.value=ele.textContent;
            initSearch();
        })
    });
}
function addBorderShadow(){
    autocompleteResults.style.borderTop = '1px solid rgba(0, 0, 0, 0.4)';
}
function removeBorderShadow(){
    autocompleteResults.style.borderTop = 'none';
}
function initSearch(){
    removeLiEle();
    removeBorderShadow();
    autocompleteResults.style.overflowY = 'hidden';
    searchParent.style.height = 45 + 'px';
    autocompleteResults.style.display = 'none';
}
export function removeLiEle(){
    const autocompleteResults = document.querySelector('#autocompleteResults');
    autocompleteResults.querySelectorAll('li').forEach(ele=>{
        ele.remove();
    });
}