export let btnCnt=0;
let btnList=[];
export function removeReview(){
        console.log("removeReview 실행");
        const reviewBoxParent = document.querySelector('.second-tab-box');
        reviewBoxParent.querySelectorAll('.review-box').forEach(ele => {
                ele.remove();
        })
}
export function setReviewData(data){
        console.log("setReviewData 실행");
        //data 반복
        data.forEach(reviewData => {
                //reviewBox 생성
                const reviewBox = document.createElement("div");
                reviewBox.classList.add("review-box");

                // 리뷰 텍스트 생성
                const reviewText = document.createElement("div");
                reviewText.classList.add("review-p");
                const pElement = document.createElement("p");
                pElement.textContent = reviewData.reviewText;
                reviewText.appendChild(pElement);

                // 리뷰어 정보 생성
                const reviewerInfo = document.createElement("div");
                reviewerInfo.classList.add("reviewer-info");

                // 리뷰 박스에 리뷰 텍스트와 리뷰어 정보 추가
                reviewBox.appendChild(reviewText);

                // 리뷰어 이름 생성
                const reviewerName = document.createElement("div");
                reviewerName.classList.add("reviewer-name");
                reviewerName.textContent = reviewData.writer;

                // 리뷰 날짜 및 점수 생성
                const reviewerEtc = document.createElement("div");
                reviewerEtc.classList.add("reviewer-etc");
                const dateElement = document.createElement("span");
                dateElement.classList.add("date");
                dateElement.textContent = reviewData.dateFormat;
                const scoreElement = document.createElement("span");
                scoreElement.classList.add("score");
                scoreElement.textContent = reviewData.score;
                reviewerEtc.appendChild(dateElement);
                reviewerEtc.appendChild(scoreElement);


                // 리뷰어 정보를 리뷰 정보에 추가
                reviewerInfo.appendChild(reviewerName);
                reviewerInfo.appendChild(reviewerEtc);

                reviewBox.appendChild(reviewerInfo);
                // 페이지에 리뷰 박스 추가
                const container = document.querySelector(".second-tab-box"); // 적절한 컨테이너 선택자를 사용하세요
                container.appendChild(reviewBox);
        });

}
export function resetReviewHeight(){
        const reviewList = document.querySelectorAll('.review-box');
        console.log("reviewList : "+reviewList.length);
        const tabEl = document.querySelector('#review-tab')
        tabEl.addEventListener('shown.bs.tab', function (event) {
                reviewList.forEach(reviewBox => {
                        const pElement = reviewBox.querySelector('.review-p p');
                        if(pElement.clientHeight>90){
                                btnCnt++;
                                btnList.push(reviewBox);
                        }
                });
                console.log("btnCnt : "+btnCnt);
                if(btnCnt > 0) {
                        btnList.forEach(reviewBox => {
                                const pElement = reviewBox.querySelector('.review-p p');
                                pElement.style.height=90+"px";
                                pElement.style.overflow='hidden';
                                const moreBtn = document.createElement("button");
                                moreBtn.classList.add("arrow-button");
                                moreBtn.addEventListener("click",function (event) {
                                        activeButton(event.target);
                                })
                                pElement.insertAdjacentElement('afterend', moreBtn);
                        });
                } else btnCnt = -1;
        })
}
function activeButton(button){
        const reviewP = button.parentElement;
        const  reviewPp = reviewP.querySelector('p');

        const priorHeight=reviewP.clientHeight;
        reviewPp.style.overflow = 'auto';
        reviewPp.style.textOverflow = 'none';
        reviewPp.style.display = 'inline';

        reviewP.style.overflow='auto';
        reviewP.style.height='auto';
        const reviewBox = reviewP.parentElement;
        const defaultHeight=reviewBox.clientHeight;

        const changeHeight = reviewP.clientHeight-priorHeight
        reviewBox.style.height = (defaultHeight+changeHeight)+'px';

        //버튼 제거
        button.remove();
}