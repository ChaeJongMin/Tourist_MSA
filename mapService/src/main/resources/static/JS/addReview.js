import {saveReview} from './callServer.js';
import {reloadReview} from './map.js';

const addReviewImg = document.querySelector('#add-review-img');
const reviewModalEle=document.getElementById('addReviewModal');
const myReviewModal = new bootstrap.Modal(reviewModalEle, {
    keyboard: false,
});
const starRatingId = 'star-rating'; // Html DOM id + star rating element name
const starRatingEl = document.getElementById(starRatingId);
const reviewAddBtn = document.querySelector('#review-add-btn');
const starRating = new Starry(starRatingEl, {
    name: starRatingId,
    labels: [
        '다시 안가',
        '부족해',
        '평범해',
        '다시 오고싶어',
        '광주 대표야'
    ],
    setStarsAfterRating : true,
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
        blank: '../IMG/Star/blank.svg',
        hover: '../IMG/Star/hover.svg',
        active: '../IMG/Star/active.svg'
    }
});

starRating.on('rate', function (rating) {
    document.querySelector('.star-score').textContent=rating+"점";
})


export function setAddReviewEvent(isReviewed){
    if(isReviewed){
        console.log("참");
    } else {
        console.log("거짓");
    }
    addReviewImg.addEventListener('click', function(){
        if(!isReviewed){
            starRating.update({
                beginWith: 0
            })
            myReviewModal.show();
        }
        else {
            alert("해당 관광지에 대한 리뷰가 있어요.. 수정이나 삭제 하고 싶으면 마이페이지에서 리뷰 수정을 해주세요!!!");
        }

    });

    reviewModalEle.addEventListener('shown.bs.modal', function(){
        const tourDestNm = document.querySelector('#touristNameA').textContent;
        document.querySelector('#review-tour-Nm').textContent = tourDestNm;
        const textAreaEle = document.querySelector('#modal-review-text');
        textAreaEle.value="";
        textAreaEle.addEventListener('keyup', function(){
            textAreaEle.style.height = 'auto';
            textAreaEle.style.height = textAreaEle.scrollHeight + 'px';
        })
        textAreaEle.addEventListener('keydown', function(){
            textAreaEle.style.height = 'auto';
            textAreaEle.style.height = textAreaEle.scrollHeight + 'px';
        })
    });

    reviewAddBtn.addEventListener('click' ,async function () {
        await saveReview(document.querySelector('#review-modal-body'));
        myReviewModal.hide();
        reloadReview();
    })
}
export function isReviewed(){

}