package com.dxs.Util;

import java.util.Random;

public class OperateId 
{  
	public String RadomGenID()
	{   
		String id = "";
		Random rd = new Random();
		for(int i = 0; i < 8; i++)
		{
			String temp = String.valueOf(rd.nextInt(10));
			id = id + temp;
		}
		return id;
	}
	
	public String GenIdAndChangeWorldId(String id,String ChangeWorldId)
	{   
		String IdAndChangeWorldId = null;
		String Sixid = id.substring(0, 6);
		String Twoid = id.substring(6, 8);
		String ChangeWorldIdFront = ChangeWorldId.substring(0,2);
		String ChangeWorldIdBack = ChangeWorldId.substring(2,4);
		
		IdAndChangeWorldId = Sixid + ChangeWorldIdFront + Twoid + ChangeWorldIdBack;
		return IdAndChangeWorldId;
	}
	
/*********************************************************************
 * ����ID��ת��ID��ϣ���ID��ת��ID��ȡ����
 *********************************************************************/
	public IdChangeWorldIdCombination ParseIdAndChangeWorldId(String IdAndChangeWorldId)
	{   
		IdChangeWorldIdCombination value = new IdChangeWorldIdCombination();
		String Sixid = IdAndChangeWorldId.substring(0, 6);
		String Twoid = IdAndChangeWorldId.substring(8, 10);
		String ChangeWorldIdFront = IdAndChangeWorldId.substring(6,8);
		String ChangeWorldIdBack = IdAndChangeWorldId.substring(10,12);
		value.setChangeWorldId(ChangeWorldIdFront+ChangeWorldIdBack);
		value.setId(Sixid+Twoid);
		return value;
	}
}
