package com.dxs.Action;

import java.util.List;

import com.dxs.Util.IdChangeWorldIdCombination;
import com.dxs.Util.OperateId;

import com.dxs.Entity.PaperBag;
import com.dxs.Entity.Tag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 壁纸修改action
 * @author  姓名 工号
 * @version  [版本号, 2014-6-18]
 */
public class PaperEdit extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String bagName;
    
    private String content;
    
    private String bookVer;
    
    private String appVer;
    
    private String uptime;
    
    private String recommendState;
    
    private String state;
    
    private String tag;
    
    private String unionId;
    
    private String unionChangeId;
    
    public String execute()
        throws Exception
    {
        PaperBagService pgServ = new PaperBagServiceImpl();
        TagService tagServ = new TagServiceImpl();
        
        PaperBag pgInfo = pgServ.getPaperBagById(getId());
        String tagname = "";
        
        if (pgInfo.getTagId() != null && !pgInfo.getTagId().equals("0"))
        {
            List<Tag> taglist = tagServ.getTagByStr_tagId(pgInfo.getTagId());
            for (int i = 0; i < taglist.size(); i++)
            {
                tagname = tagname + "," + taglist.get(i).getTagName();
            }
            tagname = tagname.substring(1, tagname.length());
        }
        else if (pgInfo.getTagId() != null && pgInfo.getTagId().equals("0"))
        {
            tagname = "";
        }
        
        ActionContext.getContext().getSession().put("pgid", pgInfo.getId());
        ActionContext.getContext().getSession().put("pgname", pgInfo.getBagName());
        setId(String.valueOf(pgInfo.getId()));
        setBagName(pgInfo.getBagName());
        setContent(pgInfo.getSummary());
        setTag(tagname);
        setAppVer(pgInfo.getVersion().substring(0, 3));
        setBookVer(pgInfo.getSver());
        setUptime(pgInfo.getUploadTime().toString());
        setRecommendState(pgInfo.getState_rec());
        setUnionId(pgInfo.getUnionId());
        
        if (null != pgInfo.getUnionId())
        {
            OperateId OPID = new OperateId();
            IdChangeWorldIdCombination value = OPID.ParseIdAndChangeWorldId(pgInfo.getUnionId());
            setUnionChangeId(value.getChangeWorldId());
        }
        
        if (pgInfo.getState().equals("1"))
        {
            setState("上架");
        }
        if (pgInfo.getState().equals("0"))
        {
            setState("未上架");
        }
        
        return "paperEdit";
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getBagName()
    {
        return bagName;
    }
    
    public void setBagName(String bagName)
    {
        this.bagName = bagName;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    public String getRecommendState()
    {
        return recommendState;
    }
    
    public void setRecommendState(String recommendState)
    {
        this.recommendState = recommendState;
    }
    
    public String getUptime()
    {
        return uptime;
    }
    
    public void setUptime(String uptime)
    {
        this.uptime = uptime;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getBookVer()
    {
        return bookVer;
    }
    
    public void setBookVer(String bookVer)
    {
        this.bookVer = bookVer;
    }
    
    public String getAppVer()
    {
        return appVer;
    }
    
    public void setAppVer(String appVer)
    {
        this.appVer = appVer;
    }
    
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    
    public String getTag()
    {
        return tag;
    }
    
    public String getUnionId()
    {
        return unionId;
    }
    
    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }
    
    public String getUnionChangeId()
    {
        return unionChangeId;
    }
    
    public void setUnionChangeId(String unionChangeId)
    {
        this.unionChangeId = unionChangeId;
    }
}
