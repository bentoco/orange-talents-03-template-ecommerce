package br.com.zupacademy.ecommerce.purchase.events;

import br.com.zupacademy.ecommerce.purchase.Purchase;

/**
 * Every event related to the success of a new purchase must implement this interface
 */
public interface PurchaseProcessedEvents {
    void process( Purchase purchase);
}
