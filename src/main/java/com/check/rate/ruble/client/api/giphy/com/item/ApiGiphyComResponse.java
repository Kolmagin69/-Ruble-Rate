package com.check.rate.ruble.client.api.giphy.com.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiGiphyComResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Data {

        private Images images;

        public Images getImages() {
            return images;
        }

        public void setImages(Images images) {
            this.images = images;
        }


        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Images {

            private FixedHeight fixed_height;

            public FixedHeight getFixed_height() {
                return fixed_height;
            }

            public void setFixed_height(FixedHeight fixed_height) {
                this.fixed_height = fixed_height;
            }


            @JsonIgnoreProperties(ignoreUnknown = true)
            public class FixedHeight {

                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
