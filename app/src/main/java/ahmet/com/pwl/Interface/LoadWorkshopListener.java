package ahmet.com.pwl.Interface;

import java.util.List;

import ahmet.com.pwl.Model.WorkShop;

public interface LoadWorkshopListener {

    void onLoadWorkShopSuccess(List<WorkShop> mLisWorksop);
    void onLoadWorkShopFailed(String error);
}
