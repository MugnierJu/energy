package energy.delivery.comparator;

import java.util.Comparator;

import energy.delivery.models.Client;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class ClientRequestComparator implements Comparator<Client>  {

    public int compare(Client c1, Client c2) {
        return ((Integer)c1.getRequest()).compareTo(c2.getRequest());
    }
}
