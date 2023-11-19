
//유저 정보 기입
export function addUserInfo(userInfo){
    document.querySelector('#name').textContent=userInfo.name;
    document.querySelector('#email').textContent=userInfo.email;
    document.querySelector('#phone').textContent=userInfo.phoneNumber;
}

//유저 정보 변경
export function updateUserInfo(email, phone){
    document.querySelector('#email').textContent=email;
    document.querySelector('#phone').textContent=phone;
}

//리뷰 박스 생성
export function addReviewInfo(reviewInfos) {
    const container = document.getElementById('my-review'); // 원하는 컨테이너의 ID로 변경하세요

    reviewInfos.forEach(review => {
        const reviewDetail = document.createElement('div');
        reviewDetail.className = 'review-detail';

        const createAndAppendElement = (tagName, className, textContent) => {
            const element = document.createElement(tagName);
            element.className = className;
            element.textContent = textContent;
            return element;
        };
        const hiddenId = (className, value) => {
            const hiddenEle = document.createElement("input")
            hiddenEle.type = "hidden";
            hiddenEle.className = className;
            hiddenEle.value = value;
            return hiddenEle;
        }
        reviewDetail.appendChild(createAndAppendElement('div', 'reviewed-tourist-name', review.tourDestNm));

        const reviewedTouristEtc = createAndAppendElement('div', 'reviewed-tourist-etc', '');
        reviewedTouristEtc.appendChild(createAndAppendElement('span', 'info-text', `점수 : ${review.score}`));
        reviewedTouristEtc.appendChild(createAndAppendElement('span', 'info-text', `작성 날짜 : ${review.dateFormat}`));
        reviewDetail.appendChild(reviewedTouristEtc);

        reviewDetail.appendChild(createAndAppendElement('div', 'reviewed-tourist-text', review.reviewText));
        reviewDetail.appendChild(hiddenId('hiddenReview', review.reviewId));

        container.appendChild(reviewDetail);


    });
}
export function updateReviewInfo(data, reviewEle) {
    const etcEle = reviewEle.querySelector('.reviewed-tourist-etc');
    //점수
    etcEle.querySelector('.info-text:first-child').textContent = "점수 : "+data.score;
    //날짜
    etcEle.querySelector('.info-text:last-child').textContent = "작성 날짜 : "+data.dateFormat;
    //내용
    document.querySelector('.reviewed-tourist-text').textContent = data.reviewText;
}
export function removeReivewInfo(parentEl){
    parentEl.remove();
}