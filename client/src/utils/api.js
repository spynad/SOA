import axios from "axios";
import convert from "xml-js";

const CATALOG_SERVICE = "https://localhost:8443/firstService-1.0-SNAPSHOT/api"
const BOOKING_SERVICE = "https://localhost:10200/api/v1/booking"

export const xml_axios = axios.create({
    headers: {
        get: {
            'Accept': 'application/xml'
        },
        delete: {
            'Accept': 'application/xml'
        },
        post: {
            'Content-Type': 'application/xml',
            'Accept': 'application/xml'
        },
        put: {
            'Content-Type': 'application/xml',
            'Accept': 'application/xml'
        }
    }
})

xml_axios.interceptors.response.use(function (response) {
    response.data = JSON.parse(convert.xml2json(response.data,{compact: true, spaces: 4, textFn:RemoveJsonTextAttribute}))
    function RemoveJsonTextAttribute(value,parentElement){
        try{
            var keyNo = Object.keys(parentElement._parent).length;
            var keyName = Object.keys(parentElement._parent)[keyNo-1];
            parentElement._parent[keyName] = value;
        }
        catch(e){}
    }
    return response;
}, function (error) {
    return Promise.reject(error);
});


xml_axios.interceptors.request.use(function (request) {
    if(request?.data != null) {
        request.data = convert.js2xml(request.data, {compact: true, ignoreComment: true, spaces: 4})
    }
    return request;
}, function (error) {
    return Promise.reject(error);
});

export const TICKETS_API = `${CATALOG_SERVICE}/ticket`
export const PERSON_API = `${CATALOG_SERVICE}/person`
export const AVERAGE_DISCOUNT = `${TICKETS_API}/get-average-ticket-discount`
export const MINIMAL_BY_CREATION_DATE = `${TICKETS_API}/get-minimal-ticket-by-creation-date`
export const CHEAPER_TICKETS = `${TICKETS_API}/get-cheaper-tickers-by-price`

export const CANCEL_BOOKING = `${BOOKING_SERVICE}/person`
export const SELL_TICKET = `${BOOKING_SERVICE}/sell`
export const OPERATION_API = `${BOOKING_SERVICE}/sell`
