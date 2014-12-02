package com.dxs.Service.Intf;

import java.util.List;

import com.dxs.Entity.PaperBag;

/**
 * 分类管理的service
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-30]
 */
public interface ClassificationMangerService
{
    /**
     * 根据分类ID获取标签ID列表
     */
    public abstract List<String> getTagIdList(String classificationId);
    
    /**
     * 根据分类ID获取壁纸ID列表,不包含标签包含的ID
     */
    public abstract List<String> getWallPaperIdListByClassificationId(String classificationId);
    
    /**
     * 根据标签ID获取壁纸ID列表
     */
    public abstract List<String> getWallPaperIdListByTagId(String tagId);
    
    /**
     * 根据分类ID和壁纸ID插入到分类壁纸关联表中去
     */
    public abstract void insertToCategoryPaper(String categoryId, String wallpaperId);
    
    /**
     * 将分类ID和标签ID插入到分类标签关联表中去
     */
    public abstract void insertToCategoryTag(String categoryId, String tagId);
    
    /**
     * 将标签ID和壁纸ID插入到壁纸标签关联表中去
     */
    public abstract void insertToTagPaper(String tagId, String wallpaperId);
    
    /**
     * 以壁纸ID为条件删除分类壁纸关联表中的数据
     */
    public abstract int deleteCategoryPaperByPaperId(String wallPaperId);
    
    /**
     * 根据分类ID获取分类标签列表
     */
    public abstract List<String> getTagIdListByClassificationId(String classificationId);
    
}
