import { check, sleep } from 'k6';
import { SharedArray } from 'k6/data';
import http from 'k6/http';

const PORT = '8002';
const BASE_URL = `http://localhost:${PORT}`;

const data = new SharedArray('platforms', function () {
    return JSON.parse(open('./platforms_list.json')).platforms;
});

export const options = {
    iterations: 6000,
    maxDuration: '30s',
    vus: 100,
    thresholds: {
        'http_req_duration': ['p(99)<1000'], // 99% of requests must complete below 1s
        'http_req_failed': ['rate<0.01'],  // http errors should be less than 1%
    }
};

export default () => {
    for (let i = 0; i < data.length; i++) {
        const res = http.get(`${BASE_URL}/platforms/${data[i].platform_urn}`);
        check(res, {
          'is status 200': (r) => r.status === 200,
        });
        sleep(1);
    }
};
