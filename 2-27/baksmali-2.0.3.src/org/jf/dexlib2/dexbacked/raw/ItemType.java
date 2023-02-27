/*    */ package org.jf.dexlib2.dexbacked.raw;
/*    */ 
/*    */ public class ItemType
/*    */ {
/*    */   public static String getItemTypeName(int itemType)
/*    */   {
/* 58 */     switch (itemType) { case 0:
/* 59 */       return "header_item";
/*    */     case 1:
/* 60 */       return "string_id_item";
/*    */     case 2:
/* 61 */       return "type_id_item";
/*    */     case 3:
/* 62 */       return "proto_id_item";
/*    */     case 4:
/* 63 */       return "field_id_item";
/*    */     case 5:
/* 64 */       return "method_id_item";
/*    */     case 6:
/* 65 */       return "class_def_item";
/*    */     case 4096:
/* 66 */       return "map_list";
/*    */     case 4097:
/* 67 */       return "type_list";
/*    */     case 4098:
/* 68 */       return "annotation_set_ref_list";
/*    */     case 4099:
/* 69 */       return "annotation_set_item";
/*    */     case 8192:
/* 70 */       return "class_data_item";
/*    */     case 8193:
/* 71 */       return "code_item";
/*    */     case 8194:
/* 72 */       return "string_data_item";
/*    */     case 8195:
/* 73 */       return "debug_info_item";
/*    */     case 8196:
/* 74 */       return "annotation_item";
/*    */     case 8197:
/* 75 */       return "encoded_array_item";
/*    */     case 8198:
/* 76 */       return "annotation_directory_item"; }
/* 77 */     return "unknown dex item type";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.ItemType
 * JD-Core Version:    0.6.0
 */