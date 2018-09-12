package ru.whitejoker.showcase;

import java.util.List;

public class OfferModel {

        private Integer id;
        private String name;
        private String info;
        private List<Offer> offers = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public List<Offer> getOffers() {
            return offers;
        }

        public void setOffers(List<Offer> offers) {
            this.offers = offers;
        }

        public class Offer {

            private Integer id;
            private String name;
            private String des;
            private String logo;
            private String url;
            private String btn;
            private Object btn2;
            private Boolean browser;
            private Boolean enabled;
            private Object descFull;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getBtn() {
                return btn;
            }

            public void setBtn(String btn) {
                this.btn = btn;
            }

            public Object getBtn2() {
                return btn2;
            }

            public void setBtn2(Object btn2) {
                this.btn2 = btn2;
            }

            public Boolean getBrowser() {
                return browser;
            }

            public void setBrowser(Boolean browser) {
                this.browser = browser;
            }

            public Boolean getEnabled() {
                return enabled;
            }

            public void setEnabled(Boolean enabled) {
                this.enabled = enabled;
            }

            public Object getDescFull() {
                return descFull;
            }

            public void setDescFull(Object descFull) {
                this.descFull = descFull;
            }

        }



}
