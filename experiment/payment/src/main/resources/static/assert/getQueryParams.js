/**
 * 获取Get传参
 */
const getQueryParams = () => {
    const queryString = window.location.search.substring(1);
    const params = {};
    const queries = queryString.split("&");

    for (let i = 0; i < queries.length; i++) {
        const pair = queries[i].split('=');
        params[pair[0]] = decodeURIComponent(pair[1] || '');
    }
    return params;
}