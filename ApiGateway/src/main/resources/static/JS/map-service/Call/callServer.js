
export async function getMapData(){
    try {
        const response = await fetch('/map-service/api/map', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
        });

        if (!response.ok) {
            throw new Error(`API 호출 실패: ${response.status}`);
        }
        return await response.json();

    } catch (e) {
        console.error('예상치 못한 오류 발생:', e);
        throw e;
    }
}

export async function getSpecificMapData(touristNm){
    try {
        const response = await fetch('/map-service/api/map/'+touristNm, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
        });

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

export async function getReviewToTourist (touristNm, userId){
    try{
        const encodedTouristNm = encodeURIComponent(touristNm);
        console.log("getReviewToTourist 작동!! "+touristNm);

        //review-service/api/
        const response = await fetch('/review-service/api/'+touristNm+'/review/'+userId,{
            method : 'GET' ,
            headers : {
                'Content-Type': 'application/json; charset=UTF-8',
            } ,
        });

        if (response.status !== 200) {
            throw new Error(`API 호출 실패: ${response.status}`);
        }

        return await response.json();

    } catch (e) {
        console.error('예상치 못한 오류 발생:', e);
        throw e;
    }
}

export async function saveReview (reviewModal){
    try{
        const tourDestNm = reviewModal.querySelector('#review-tour-Nm').textContent;
        const score = parseFloat(reviewModal.querySelector('.star-score').textContent.split("/")[0]);
        const reviewText = reviewModal.querySelector('#modal-review-text').value;
        const userNm = document.querySelector('#user-id').textContent.split(" ")[0];
        console.log("이름 : "+document.querySelector('#user-id').textContent);
        const data = {
            tourDestNm : tourDestNm,
            userNm : userNm ,
            reviewText : reviewText,
            score : score
        }
        //review-service/api/
        const response = await fetch('/review-service/api/review',{
            method : 'POST' ,
            headers : {
                'Content-Type': 'application/json; charset=UTF-8',
            } ,
            body : JSON.stringify(data),
        });
        if (response.status !== 200) {
            throw new Error(`API 호출 실패: ${response.status}`);
        }
        return await response.json();
    } catch (e) {
        console.error('예상치 못한 오류 발생:', e);
        throw e;
    }
}
