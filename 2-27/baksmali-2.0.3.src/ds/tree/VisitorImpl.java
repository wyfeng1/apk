/*    */ package ds.tree;
/*    */ 
/*    */ public abstract class VisitorImpl<T, R>
/*    */   implements Visitor<T, R>
/*    */ {
/*    */   protected R result;
/*    */ 
/*    */   public VisitorImpl()
/*    */   {
/* 15 */     this.result = null;
/*    */   }
/*    */ 
/*    */   public R getResult()
/*    */   {
/* 23 */     return this.result;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     ds.tree.VisitorImpl
 * JD-Core Version:    0.6.0
 */