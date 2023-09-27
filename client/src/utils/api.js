const CATALOG_SERVICE = "https://localhost:4500/api/v1/catalog"
const BOOKING_SERVICE = "https://localhost:4600/api/v1/booking"

export const TICKETS_API = `${CATALOG_SERVICE}/ticket`
export const AVERAGE_DISCOUNT = `${TICKETS_API}/get-average-ticket-discount`
export const MINIMAL_BY_CREATION_DATE = `${TICKETS_API}/get-minimal-ticket-by-creation-date`
export const CHEAPER_TICKETS = `${TICKETS_API}/get-cheaper-tickers-by-price`

export const CANCEL_BOOKING = `${BOOKING_SERVICE}/person`
export const SELL_TICKET = `${BOOKING_SERVICE}/sell`
export const OPERATION_API = `${BOOKING_SERVICE}/sell`