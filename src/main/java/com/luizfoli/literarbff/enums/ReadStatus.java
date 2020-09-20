package com.luizfoli.literarbff.enums;

public enum ReadStatus {
    WANNA_READ(0),
    READING(1),
    READ(2);

    private int status;

    ReadStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
