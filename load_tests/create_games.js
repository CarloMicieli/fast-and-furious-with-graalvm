import { check, sleep } from 'k6';
import { SharedArray } from 'k6/data';
import http from 'k6/http';

const PORT = '8004';
const BASE_URL = `http://localhost:${PORT}`;

const data = new SharedArray('games', function () {
    return JSON.parse(open('./games_list.json')).games;
});

export const options = {
    iterations: 1,
    vus: 1,
    thresholds: {
        'http_req_duration': ['p(99)<1000'], // 99% of requests must complete below 1s
        'http_req_failed': ['rate<0.01'],  // http errors should be less than 1%
    }
};

export default () => {
    for (let i = 0; i < data.length; i++) {
        const game = data[i];
        const payload = JSON.stringify(game);

        const params = {
            headers: {
                'Content-Type': 'application/json',
            },
        };

        const res = http.post(`${BASE_URL}/games`, payload, params);
        check(res, {
          'is status 201': (r) => r.status === 201,
        });
        //sleep(1);
    }
};
