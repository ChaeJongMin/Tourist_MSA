
export async function getMapData(){
    try {
        const response = await fetch('/api/map', {
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
        const response = await fetch('/api/map/'+touristNm, {
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