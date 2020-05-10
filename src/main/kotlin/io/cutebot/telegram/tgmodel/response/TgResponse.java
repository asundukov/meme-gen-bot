package io.cutebot.telegram.tgmodel.response;

public class TgResponse<T>  {
    public Boolean ok;
    public T result;

    @Override
    public String toString() {
        return "TgResponse{" +
                "ok=" + ok +
                ", result=" + result +
                '}';
    }
}
