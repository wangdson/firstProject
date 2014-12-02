package com.dxs.Service.Intf;

import java.util.Date;
import java.util.List;

import com.dxs.Entity.CompileQueue;
import com.dxs.Entity.PaperBag;
import com.dxs.Entity.PaperBag_simple;

public interface PaperBagService
{   
    /**
     * 删除图片包
     */
    public abstract int DelById(String id);
    
    public abstract int modifyVerSionById(String id, String ver);
    
    public abstract int modifySverById(String id, String sver);
    
    public abstract int modifyStateById(String id, int a);
    
    public abstract int modifyStateById(String id, int a, Date dd);
    
    public abstract int modifyInitById(String id, String a);
    
    public abstract int modifyById(String isbn, PaperBag pg);
    
    public abstract int addPaperBag(PaperBag pg);
    
    public abstract List<PaperBag> getAllPaperBag();
    
    /**
     * 提取在分类壁纸关联表中的数据
     */
    public abstract List<PaperBag> getPaperBagByInCategoryPaperIdList(String slistCondition);
    
    /**
     * 提取不在分类壁纸关联表中的数据
     */
    public abstract List<PaperBag> getPaperBagByNotInCategoryPaperIdList(String slistCondition);
    
    public abstract List<PaperBag> getAllPaperBag(int cpage, int cnum);
    
    public abstract List<PaperBag> getAllPaperBag(int cpage, int cnum,int pageSize);
    
    public abstract int findbagCount();
    
    public abstract int findstandCount();
    
    public abstract int findRecCount();
    
    public abstract int modifyRecStateById(String id, String recstate, Date recDate);
    
    public abstract int modifyRecStateById(String id, String recstate);
    
    public abstract List<PaperBag> getlastPaper(int cp,int num);
    
    public abstract List<PaperBag> getTopPaper(int cp,int num);
    
    public abstract List<PaperBag> getJoinPaper(int cp,int num);
    
    public abstract List<PaperBag> getlastPaper(int cp,int num,int pageSize);
    
    public abstract List<PaperBag> getTopPaper(int cp,int num,int pageSize);
    
    public abstract List<PaperBag> getJoinPaper(int cp,int num,int pageSize);
    
    public abstract int lastPage_hot();
    
    public abstract int lastPage_rec();
    
    public abstract int lastPage();
    
    public abstract List<PaperBag_simple> getPaperBag_simpleByCateId(int cateId);
    
    public abstract List<PaperBag_simple> getPaperBag_simpleNoCateId();
    
    public abstract List<PaperBag_simple> getPaperBag_simpleByTagId(int tagId);
    
    public abstract List<PaperBag_simple> getPaperBag_simpleByTagId(int cateId, int tagId, int limit);
    
    public abstract List<PaperBag_simple> getPaperBag_simpleByTagIdAndpng(int cateId, int tagId, int limit);
    
    public abstract List<PaperBag> getPaperBagByTagId(int tagId);
    
    public abstract List<PaperBag> getPaperBagByCateId_cutsum(int cp, int cateId);
    
    public abstract int modifyPaperBagCateIdById(int id, int tocateId);
    
    public abstract int modifyPaperBagCateId(int tocateId, int fromcateId);
    
    public abstract PaperBag_simple getPaperBag_simpleByName(String name);
    
    public abstract int modifyVerNumById_noInitial(String id, String ver);
    
    public abstract List<PaperBag> getPaperBagByTagId_cutsum(int cp, int tagId);
    
    public abstract int lastPage_tag(int tagId);
    
    public abstract int lastPage_cate(int cateId);
    
    public abstract List<PaperBag> searchBook(String keyword);
    
    public abstract List<PaperBag_simple> getPaperBag_simpleByCateIdAndpng(int cateId);
    
    public abstract int modifyTagIdById(String pgid, String pgTagId, int cateId);
    
    public abstract int modifyTagIdById(String pgid, String pgTagId);
    
    public abstract int getCountPaperBycateId(int cateId);
    
    public abstract int lastPage_cate_total(int subtotal);
    
    public abstract List<PaperBag_simple> getPaperBag_simpleByCateIdAndpng(int cp, int cateId);
    
    public abstract List<CompileQueue> getCompileQueueList();
    
    public abstract int findHotPaperCount();
    
    /**
     * 添加编译队列
     */
    public abstract void addCompileQueue(String name,int id);
    
    /**
     * 删除编译队列
     */
    public abstract void deleteCompileQueue(String bgId);
    
    /**
     * 获取所有壁纸名字的列表
     */
    public abstract List<String> getAllPageNameList();
    
    /**
     * 通过名称获取壁纸信息
     */
    public abstract PaperBag getPaperBagByName(String name);
    
    /**
     * 通过壁纸ID获取壁纸信息
     */
    public abstract PaperBag getPaperBagById(String id);
    
    /**
     * 找出编译列表中的最大值
     */
    public abstract int findCompileMaxId();
    
    /**
     * 找出最大ID
     */
    public abstract int findMaxId();
    
    /**
     * 查找此ID的包是否存在
     */
    public abstract Boolean wallPaperExistOrNot(String id);
    /**
     * 获取资源版本号
     */
    public abstract String getSverById(String id);
}
