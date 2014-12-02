package com.dxs.Service.Intf;

import java.util.List;

import com.dxs.Entity.Tag;
import com.dxs.Entity.Tag_simple;
import com.dxs.Entity.Variable;

/**
 * 标签service
 * @author  姓名 工号
 * @version  [版本号, 2014-6-27]
 */
public interface TagService
{   
    /**
     * 添加标签
     */
    public abstract int addTag(Tag tag);
    
    /**
     * 删除标签
     */
    public abstract int DelByTagId(int tagId);
    
    /**
     * 删除标签
     */
    public abstract int DelByTagName(String name);
    
    /**
     * 根据分类删除标签
     */
    public abstract int DelAllByCategoryId(int categoryId);
    
    /**
     * 找出最大Id
     */
    public abstract int findMaxTagId();
    
    public abstract int findMaxTagCount();
    
    public abstract int updateTagCateById(int cate, int id);
    
    public abstract int updateTagCateByCatId(int cateId);
    
    public abstract int updateTagCateAndHotById(int cate, int hotNum, int id);
    
    public abstract int updateTagHotById(int id);
    
    /**
     * 根据分类ID获取标签
     */
    public abstract List<Tag> getTagByCategoryId(String categoryId, int limit);
    
    public abstract List<Tag> getTagByStr_tagId(String tagId);
    
    public abstract List<Tag> getAllTagList();
    
    public abstract List<Tag> getAllTagOrderByHotNum();
    
    public abstract List<String> getIdByName(String name);
    
    public abstract int getintIdByName(String name);
    
    public abstract Tag searchTagByName(String name);
    
    public abstract List<Tag_simple> getSimpleTagByCategoryId(String categoryId, int limit);
    
    public abstract List<Tag_simple> getSimpleTag(int limit);
    
    public abstract List<Variable> getTagNameByStr_tagId(String tagId);
    
    
    /**
     * 根据分类ID查询存在的分类标签关联表
     */
    public abstract List<Tag> getTagIdListByInClassificationId(String slistCondition);
    
    /**
     * 根据分类ID查询不存在分类标签关联表
     */
    public abstract List<Tag> getTagIdListByNotInClassificationId(String slistCondition);
    
}
