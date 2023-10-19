export async function getMyInfo(){
    try {
        const userId="dico2760";
        const response = await fetch('/user-service/api/user/'+userId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
        });
        console.log("받아온 상태값: "+response.status);
        if (!response.ok) {
            throw new Error(`API 호출 실패: ${response.status}`);
        }
        const data=await response.json();
        return data;

    } catch (e) {
        console.error('예상치 못한 오류 발생:', e);
        throw e;
    }
}
export async function updateMyInfo(){
    try {
        const userId="dico2760";
        const email = document.querySelector('#edit-email').value;
        const phone = document.querySelector('#edit-tel').value;

        const requestData = {
            email : email,
            phone : phone
        }
        const response = await fetch('/user-service/api/user/'+userId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
            body : JSON.stringify(requestData),
        });
        console.log("받아온 상태값: "+response.status);
        if (!response.ok) {
            throw new Error(`API 호출 실패: ${response.status}`);
        }
        const data=await response.json();


    } catch (e) {
        console.error('예상치 못한 오류 발생:', e);
        throw e;
    }
}

export async function updateReview(reviewId){
    try {

        const reviewText = document.querySelector('#modal-review-text').value;
        const score = document.querySelector('.star-score').textContent;

        const requestData = {
            reviewTexts : reviewText,
            score : parseFloat(score)
        }

        const response = await fetch('http://host.docker.internal:10906/review-service/api/review/'+reviewId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
            body : JSON.stringify(requestData),
        });
        console.log("받아온 상태값: "+response.status);
        if (!response.ok) {
            throw new Error(`API 호출 실패: ${response.status}`);
        }
        return await response.json();

    } catch (e) {
        console.error('예상치 못한 오류 발생:', e);
        throw e;
    }
}

export async function removeReview(reviewId){
    try {

        const response = await fetch('http://host.docker.internal:10906/review-service/api/review/'+reviewId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
        });
        console.log("받아온 상태값: "+response.status);
        if (!response.ok) {
            throw new Error(`API 호출 실패: ${response.status}`);
        }


    } catch (e) {
        console.error('예상치 못한 오류 발생:', e);
        throw e;
    }
}
