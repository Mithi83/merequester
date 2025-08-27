package com.almostreliable.merequester.requester.status;

public class CpuState extends BlockingState {

    CpuState() {}

    @Override
    public RequestStatus type() {
        return RequestStatus.CPU;
    }
}
