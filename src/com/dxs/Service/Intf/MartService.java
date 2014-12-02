package com.dxs.Service.Intf;

import java.util.List;

import com.dxs.Entity.Mart;
import com.dxs.Entity.MartResult;

public interface MartService {
	public abstract int modifyNumById(String paperId,String mid);
	public abstract int addmart(Mart mart);
	public abstract Mart getMartByPaperId(String paperId,String mid);
	public abstract List<MartResult> getMartResult();

}
