import {removeReview, updateMyInfo, updateReview} from '../Call/MyPageFetch.js';
import {updateUserInfo , updateReviewInfo, removeReivewInfo} from '../Element/addElement.js';

const review = document.querySelector('.review-detail');
const modalEle=document.getElementById('exampleModal');
const myInfoModalEle=document.getElementById('myInfoModal');
const myModal = new bootstrap.Modal(modalEle, {
    keyboard: false,
});
const myInfoModal = new bootstrap.Modal(myInfoModalEle, {
    keyboard: false,
});
const infoEditBtn=document.querySelector('.myInfo-edit-btn');
const myInfoBtn = document.querySelector('#myInfo-update-Btn');
const reviewInfoBtn = document.querySelector('#review-update-Btn');
const reviewRemoveInfoBtn = document.querySelector('#review-remove-Btn');

const starRatingId = 'star-rating'; // Html DOM id + star rating element name
const starRatingEl = document.getElementById(starRatingId);
const starRating = new Starry(starRatingEl, {
    name: starRatingId,
    labels: [
        '다시 안가',
        '부족해',
        '평범해',
        '다시 오고싶어',
        '광주 대표야'
    ],
    onClear: function () {
        $('[data-name="' + starRatingId + '"] [data-tooltip]').tooltip('dispose');
    },
    onRender: function () {
        $('[data-name="' + starRatingId + '"] [data-tooltip]').tooltip({
            trigger: 'hover',
            placement: 'top'
        });
    },
    onRate: function (rating) {
        console.log(rating)
    },
    icons: {
        // File path, uri or base64 string for `src` attribute
        blank: '../../IMG/Common/Star/blank.svg',
        hover: '../../IMG/Common/Star/hover.svg',
        active: '../../IMG/Common/Star/active.svg'
    }
});

let currentReviewEle;
let currentTourNm;
let currentReviewText;

export function myInfoModalEvent(){

    infoEditBtn.addEventListener('click', function(){
        myInfoModal.show();
    });

    myInfoModalEle.addEventListener('shown.bs.modal', function(){
        document.querySelector('#edit-email').value=document.querySelector('#email').textContent;
        document.querySelector('#edit-tel').value=document.querySelector('#phone').textContent;
    });
}
export function reviewModalEvent(){
    const reviewEleList = document.querySelectorAll('.review-detail');
    reviewEleList.forEach( review => {
        review.addEventListener('click', function(){
            currentReviewEle=review;
            myModal.show();
        });
    });

    modalEle.addEventListener('shown.bs.modal', function(){
        document.querySelector('.modal-review-name').textContent=currentTourNm;
        const tourDestNm = currentReviewEle.querySelector('.reviewed-tourist-name').textContent;
        const scoreText = currentReviewEle.querySelector('.info-text:first-child').textContent.split(" ")[2];
        const reviewText = currentReviewEle.querySelector('.reviewed-tourist-text').textContent;

        //관광지명
        document.querySelector('.modal-review-name').textContent=tourDestNm;

        //별점
        const score = parseInt(scoreText, 10);
        document.querySelector('.star-score').textContent=score+" 점";
        starRating.update({
            beginWith: (20*score)
        })

        //리뷰
        const textarea =  document.querySelector('#modal-review-text');
        textarea.value=reviewText;
        textarea.style.height = 'auto'; // 높이를 초기화합니다.
        textarea.style.height = textarea.scrollHeight + 'px'; // 내용에
        console.log("별점: "+starRating.getCurrentRating());

    });
}
//리뷰
export function updateBtnEvent(userId){
    myInfoBtn.addEventListener('click',async function () {

        const email = document.querySelector('#edit-email').value;
        const phone = document.querySelector('#edit-tel').value
        updateUserInfo(email, phone);

        await updateMyInfo(userId);

        myInfoModal.hide();

    })
    reviewInfoBtn.addEventListener('click',async function (event) {
        const reviewId = currentReviewEle.querySelector('.hiddenReview').value;
        const updateData = await updateReview(reviewId);
        updateReviewInfo(updateData, currentReviewEle);
        myModal.hide();
    })
    reviewRemoveInfoBtn.addEventListener('click', async function(event){
        const reviewId = currentReviewEle.querySelector('.hiddenReview').value;
        await removeReview(reviewId)
        removeReivewInfo(currentReviewEle);
        myModal.hide();
    })
}


starRating.on('rate', function (rating) {
    document.querySelector('.star-score').textContent=rating+" 점";
})




