package com.yart.literule.core.model.decision;

import com.yart.literule.core.rule.RStatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseDecision implements Decision {
    private RStatus status = RStatus.Draft;
    private String ver;

    final static Set<RStatus> aliveSet = new HashSet<>(
            Arrays.asList(RStatus.Test,RStatus.Stage,RStatus.Up)
    );

    public boolean isAlive() {
        return aliveSet.contains(status);
    }

    public RStatus getStatus() {
        return status;
    }

    public void setStatus(RStatus status) {
        this.status = status;
    }

    @Override
    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
}
