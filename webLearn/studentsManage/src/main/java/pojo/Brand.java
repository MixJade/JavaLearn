package pojo;

public class Brand {
    int brandId;
    String brandName;
    String brandDescribe;
    int brandStatus;

    public Brand(int brandId, String brandName, String brandDescribe, int brandStatus) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandDescribe = brandDescribe;
        this.brandStatus = brandStatus;
    }

    public int getBrandId() {
        return brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getBrandDescribe() {
        return brandDescribe;
    }

    public int getBrandStatus() {
        return brandStatus;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", brandDescribe='" + brandDescribe + '\'' +
                ", brandStatus=" + brandStatus +
                '}';
    }
}
