package com.dev.Service;

import com.dev.DAO.ProductDAO;
import com.dev.VO.Behavior;

import java.util.List;

public class Service {
    private static final Service service = new Service();

    private Service() { }

    public static Service getInstance() { return service; }

    public void insert(List<Behavior> behaviors) {
        ProductDAO.getInstance().insert(behaviors);
    }

    public int search(String productName, String searchOption) {
        return ProductDAO.getInstance().search(productName, searchOption);
    }

    public List<Behavior> load(String productName, String searchOption) {
        return ProductDAO.getInstance().load(productName, searchOption);
    }
}
