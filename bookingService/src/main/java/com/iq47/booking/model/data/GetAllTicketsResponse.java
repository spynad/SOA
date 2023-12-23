package com.iq47.booking.model.data;


import com.sun.xml.txw2.annotation.XmlNamespace;

import javax.xml.bind.annotation.*;

/**
 * <p>Java class for getAllTicketsResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="getAllTicketsResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://wsservice.spynad.com/}ticketListResult" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAllTicketsResponse", propOrder = {
        "_return"
})
public class GetAllTicketsResponse {

    @XmlElement(name = "return")
    protected TicketListResult _return;

    /**
     * Gets the value of the return property.
     *
     * @return
     *     possible object is
     *     {@link TicketListResult }
     *
     */
    public TicketListResult getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     *
     * @param value
     *     allowed object is
     *     {@link TicketListResult }
     *
     */
    public void setReturn(TicketListResult value) {
        this._return = value;
    }

}

