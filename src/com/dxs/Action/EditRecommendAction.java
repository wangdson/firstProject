package com.dxs.Action;

import java.util.List;
import java.util.Map;

import com.dxs.Entity.Retrieval;
import com.dxs.Service.Impl.RetrievalServiceImpl;
import com.dxs.Service.Intf.RetrievalService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EditRecommendAction extends ActionSupport
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3675359194251120995L;
    
    private int retrievalId;
    
    private String flag;
    
    private String userKeyWord;
    
    private String cate;
    
    private List<Retrieval> retrievalList;
    
    private int pageNo;
    
    private int pageSize;
    
    private int tcount;
    
    public List<Retrieval> getRetrievalList()
    {
        return retrievalList;
    }
    
    public void setRetrievalList(List<Retrieval> retrievalList)
    {
        this.retrievalList = retrievalList;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getTcount()
    {
        return tcount;
    }
    
    public void setTcount(int tcount)
    {
        this.tcount = tcount;
    }
    
    public String getUserKeyWord()
    {
        return userKeyWord;
    }
    
    public void setUserKeyWord(String userKeyWord)
    {
        this.userKeyWord = userKeyWord;
    }
    
    public String getFlag()
    {
        return flag;
    }
    
    public void setFlag(String flag)
    {
        this.flag = flag;
    }
    
    public int getRetrievalId()
    {
        return retrievalId;
    }
    
    public void setRetrievalId(int retrievalId)
    {
        this.retrievalId = retrievalId;
    }
    
    public String execute()
        throws Exception
    {
        Map<String, Object> session = ActionContext.getContext().getSession();
        String firstWord = "", id = "", newword = "", firstCate = "", newCate = "";
        RetrievalService retrievalService = new RetrievalServiceImpl();
        if (getFlag().equals("fromEdit"))
        {// ����� �ؼ����б��йؼ��ֵĳ�����
            session.put("retId", getRetrievalId());// ��id��Session
            Retrieval ret = retrievalService.getRetrievalById(getRetrievalId());// ���id���Retrieval
            session.put("ret", ret);
            firstWord = ret.getUserKeyWord();
            firstCate = ret.getCate();
            setUserKeyWord(firstWord);
            setCate(firstCate);
            session.put("result", null);
        }
        else if (getFlag().equals("toEdit"))
        {
            // Ϊ�˸��£��ٴλ��һ���ı����ֵ����submit��ť�������action,��ʱ��action��userKeyWord��form��������
            newword = getUserKeyWord().toString();
            newCate = getCate().toString();
            id = String.valueOf(session.get("retId"));// �Ӵ������ȡ id
            /*
             * Pattern pattern = Pattern.compile("[0-9]*"); //����������ʽ���жϴ������Ƿ������� if (!newword.equals("") &&
             * newword!=firstWord &&!pattern.matcher(newword).matches()) { //��Ϊ�������֣����Ը��id�Ĺؼ��
             * id=String.valueOf(session.get("retId")) ;//�Ӵ������ȡ id
             * retrievalService.modifyWordById(Integer.parseInt(id), newword); }else if (!newword.equals("") &&
             * newword!=firstWord && pattern.matcher(newword).matches()) { //�����֣����id�ļ�������
             * id=String.valueOf(session.get("retId")) ;//�Ӵ������ȡ id retrievalService
             * .modifyNumById(Integer.parseInt(id),Integer.parseInt(newword)); }
             */
            if (newword != null && newCate != null && !newword.equals("") && !newCate.equals(""))
            {
                retrievalService.modifyWordCateById(Integer.parseInt(id), newword, newCate);
                session.put("ret", null);
                session.put("result", "ok");
                setPageNo(pageNo);
                setPageSize(pageSize);
                retrievalList = retrievalService.getlastRetrieval(pageNo, pageSize);
                ActionContext.getContext().getSession().put("retrievalList", retrievalList);
            }
        }
        
        return "retrieval";
    }
    
    public void setCate(String cate)
    {
        this.cate = cate;
    }
    
    public String getCate()
    {
        return cate;
    }
    
}
