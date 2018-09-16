package ru.whitejoker.showcase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

//Класс для формирования JSON объекта
public class OfferModel {

        private Integer id;
        private String name;
        private String info;
        private List<Offer> offers = null;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getInfo() {
            return info;
        }

        public List<Offer> getOffers() {
            return offers;
        }

        public class Offer {

            private Integer id;
            private String name;
            private String des;
            private String logo;
            private String url;
            private String btn;
            private String btn2;
            @SerializedName("desc_full") //необходимо для того чтоы десериализовать поле, т.к. имя отличчается от имени в json
            @Expose
            private String descFull;
            private Boolean browser;
            private Boolean enabled;


            public Integer getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getDes() {
                return des;
            }

            public String getLogo() {
                return logo;
            }

            public String getUrl() {
                return url;
            }

            public String getBtn() {
                return btn;
            }

            public String getBtn2() {
                return btn2;
            }

            public Boolean getBrowser() {
                return browser;
            }

            public Boolean getEnabled() {
                return enabled;
            }

            public String getDescFull() {
                return descFull;
            }
        }



}
