/*     */ package org.jf.dexlib2.writer.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.jf.dexlib2.base.BaseTryBlock;
/*     */ import org.jf.dexlib2.iface.ExceptionHandler;
/*     */ import org.jf.dexlib2.iface.TryBlock;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class TryListBuilder<EH extends ExceptionHandler>
/*     */ {
/*     */   private final MutableTryBlock<EH> listStart;
/*     */   private final MutableTryBlock<EH> listEnd;
/*     */ 
/*     */   public TryListBuilder()
/*     */   {
/*  54 */     this.listStart = new MutableTryBlock(0, 0);
/*  55 */     this.listEnd = new MutableTryBlock(0, 0);
/*  56 */     this.listStart.next = this.listEnd;
/*  57 */     this.listEnd.prev = this.listStart;
/*     */   }
/*     */ 
/*     */   public static <EH extends ExceptionHandler> List<TryBlock<EH>> massageTryBlocks(List<? extends TryBlock<? extends EH>> tryBlocks)
/*     */   {
/*  62 */     TryListBuilder tlb = new TryListBuilder();
/*     */ 
/*  64 */     for (TryBlock tryBlock : tryBlocks) {
/*  65 */       startAddress = tryBlock.getStartCodeAddress();
/*  66 */       endAddress = startAddress + tryBlock.getCodeUnitCount();
/*     */ 
/*  68 */       for (ExceptionHandler exceptionHandler : tryBlock.getExceptionHandlers())
/*  69 */         tlb.addHandler(startAddress, endAddress, exceptionHandler);
/*     */     }
/*     */     int startAddress;
/*     */     int endAddress;
/*  72 */     return tlb.getTryBlocks();
/*     */   }
/*     */ 
/*     */   private TryBounds<EH> getBoundingRanges(int startAddress, int endAddress)
/*     */   {
/* 192 */     MutableTryBlock startBlock = null;
/*     */ 
/* 194 */     MutableTryBlock tryBlock = this.listStart.next;
/* 195 */     while (tryBlock != this.listEnd) {
/* 196 */       int currentStartAddress = tryBlock.startCodeAddress;
/* 197 */       int currentEndAddress = tryBlock.endCodeAddress;
/*     */ 
/* 199 */       if (startAddress == currentStartAddress)
/*     */       {
/* 203 */         startBlock = tryBlock;
/* 204 */         break;
/* 205 */       }if ((startAddress > currentStartAddress) && (startAddress < currentEndAddress))
/*     */       {
/* 211 */         startBlock = tryBlock.split(startAddress);
/* 212 */         break;
/* 213 */       }if (startAddress < currentStartAddress) {
/* 214 */         if (endAddress <= currentStartAddress)
/*     */         {
/* 219 */           startBlock = new MutableTryBlock(startAddress, endAddress);
/* 220 */           tryBlock.prepend(startBlock);
/* 221 */           return new TryBounds(startBlock, startBlock);
/*     */         }
/*     */ 
/* 228 */         startBlock = new MutableTryBlock(startAddress, currentStartAddress);
/* 229 */         tryBlock.prepend(startBlock);
/* 230 */         break;
/*     */       }
/*     */ 
/* 234 */       tryBlock = tryBlock.next;
/*     */     }
/*     */ 
/* 242 */     if (startBlock == null) {
/* 243 */       startBlock = new MutableTryBlock(startAddress, endAddress);
/* 244 */       this.listEnd.prepend(startBlock);
/* 245 */       return new TryBounds(startBlock, startBlock);
/*     */     }
/*     */ 
/* 248 */     tryBlock = startBlock;
/* 249 */     while (tryBlock != this.listEnd) {
/* 250 */       int currentStartAddress = tryBlock.startCodeAddress;
/* 251 */       int currentEndAddress = tryBlock.endCodeAddress;
/*     */ 
/* 253 */       if (endAddress == currentEndAddress)
/*     */       {
/* 257 */         return new TryBounds(startBlock, tryBlock);
/* 258 */       }if ((endAddress > currentStartAddress) && (endAddress < currentEndAddress))
/*     */       {
/* 264 */         tryBlock.split(endAddress);
/* 265 */         return new TryBounds(startBlock, tryBlock);
/* 266 */       }if (endAddress <= currentStartAddress)
/*     */       {
/* 272 */         MutableTryBlock endBlock = new MutableTryBlock(tryBlock.prev.endCodeAddress, endAddress);
/* 273 */         tryBlock.prepend(endBlock);
/* 274 */         return new TryBounds(startBlock, endBlock);
/*     */       }
/* 276 */       tryBlock = tryBlock.next;
/*     */     }
/*     */ 
/* 284 */     MutableTryBlock endBlock = new MutableTryBlock(this.listEnd.prev.endCodeAddress, endAddress);
/* 285 */     this.listEnd.prepend(endBlock);
/* 286 */     return new TryBounds(startBlock, endBlock);
/*     */   }
/*     */ 
/*     */   public void addHandler(int startAddress, int endAddress, EH handler) {
/* 290 */     TryBounds bounds = getBoundingRanges(startAddress, endAddress);
/*     */ 
/* 292 */     MutableTryBlock startBlock = bounds.start;
/* 293 */     MutableTryBlock endBlock = bounds.end;
/*     */ 
/* 295 */     int previousEnd = startAddress;
/* 296 */     MutableTryBlock tryBlock = startBlock;
/*     */     do
/*     */     {
/* 305 */       if (tryBlock.startCodeAddress > previousEnd) {
/* 306 */         MutableTryBlock newBlock = new MutableTryBlock(previousEnd, tryBlock.startCodeAddress);
/* 307 */         tryBlock.prepend(newBlock);
/* 308 */         tryBlock = newBlock;
/*     */       }
/*     */ 
/* 311 */       tryBlock.addHandler(handler);
/* 312 */       previousEnd = tryBlock.endCodeAddress;
/* 313 */       tryBlock = tryBlock.next;
/* 314 */     }while (tryBlock.prev != endBlock);
/*     */   }
/*     */ 
/*     */   public List<TryBlock<EH>> getTryBlocks() {
/* 318 */     return Lists.newArrayList(new Iterator()
/*     */     {
/*     */       private TryListBuilder.MutableTryBlock<EH> next;
/*     */ 
/*     */       protected TryListBuilder.MutableTryBlock<EH> readNextItem()
/*     */       {
/* 333 */         TryListBuilder.MutableTryBlock ret = this.next.next;
/*     */ 
/* 335 */         if (ret == TryListBuilder.this.listEnd) {
/* 336 */           return null;
/*     */         }
/*     */ 
/* 339 */         while ((ret.next != TryListBuilder.this.listEnd) && 
/* 340 */           (ret.endCodeAddress == ret.next.startCodeAddress) && (ret.getExceptionHandlers().equals(ret.next.getExceptionHandlers())))
/*     */         {
/* 342 */           ret.mergeNext();
/*     */         }
/*     */ 
/* 347 */         return ret;
/*     */       }
/*     */ 
/*     */       public boolean hasNext() {
/* 351 */         return this.next != null;
/*     */       }
/*     */ 
/*     */       public TryBlock<EH> next() {
/* 355 */         if (!hasNext()) {
/* 356 */           throw new NoSuchElementException();
/*     */         }
/* 358 */         TryBlock ret = this.next;
/* 359 */         this.next = readNextItem();
/*     */ 
/* 361 */         return ret;
/*     */       }
/*     */ 
/*     */       public void remove() {
/* 365 */         throw new UnsupportedOperationException();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private static class MutableTryBlock<EH extends ExceptionHandler> extends BaseTryBlock<EH>
/*     */   {
/* 100 */     public MutableTryBlock<EH> prev = null;
/* 101 */     public MutableTryBlock<EH> next = null;
/*     */     public int startCodeAddress;
/*     */     public int endCodeAddress;
/* 105 */     public List<EH> exceptionHandlers = Lists.newArrayList();
/*     */ 
/*     */     public MutableTryBlock(int startCodeAddress, int endCodeAddress) {
/* 108 */       this.startCodeAddress = startCodeAddress;
/* 109 */       this.endCodeAddress = endCodeAddress;
/*     */     }
/*     */ 
/*     */     public MutableTryBlock(int startCodeAddress, int endCodeAddress, List<EH> exceptionHandlers)
/*     */     {
/* 114 */       this.startCodeAddress = startCodeAddress;
/* 115 */       this.endCodeAddress = endCodeAddress;
/* 116 */       this.exceptionHandlers = Lists.newArrayList(exceptionHandlers);
/*     */     }
/*     */ 
/*     */     public int getStartCodeAddress() {
/* 120 */       return this.startCodeAddress;
/*     */     }
/*     */ 
/*     */     public int getCodeUnitCount() {
/* 124 */       return this.endCodeAddress - this.startCodeAddress;
/*     */     }
/*     */ 
/*     */     public List<EH> getExceptionHandlers() {
/* 128 */       return this.exceptionHandlers;
/*     */     }
/*     */ 
/*     */     public MutableTryBlock<EH> split(int splitAddress)
/*     */     {
/* 133 */       MutableTryBlock newTryBlock = new MutableTryBlock(splitAddress, this.endCodeAddress, this.exceptionHandlers);
/* 134 */       this.endCodeAddress = splitAddress;
/* 135 */       append(newTryBlock);
/* 136 */       return newTryBlock;
/*     */     }
/*     */ 
/*     */     public void delete() {
/* 140 */       this.next.prev = this.prev;
/* 141 */       this.prev.next = this.next;
/*     */     }
/*     */ 
/*     */     public void mergeNext()
/*     */     {
/* 146 */       this.endCodeAddress = this.next.endCodeAddress;
/* 147 */       this.next.delete();
/*     */     }
/*     */ 
/*     */     public void append(MutableTryBlock<EH> tryBlock) {
/* 151 */       this.next.prev = tryBlock;
/* 152 */       tryBlock.next = this.next;
/* 153 */       tryBlock.prev = this;
/* 154 */       this.next = tryBlock;
/*     */     }
/*     */ 
/*     */     public void prepend(MutableTryBlock<EH> tryBlock) {
/* 158 */       this.prev.next = tryBlock;
/* 159 */       tryBlock.prev = this.prev;
/* 160 */       tryBlock.next = this;
/* 161 */       this.prev = tryBlock;
/*     */     }
/*     */ 
/*     */     public void addHandler(EH handler) {
/* 165 */       for (ExceptionHandler existingHandler : this.exceptionHandlers) {
/* 166 */         String existingType = existingHandler.getExceptionType();
/* 167 */         String newType = handler.getExceptionType();
/*     */ 
/* 170 */         if (existingType == null) {
/* 171 */           if (newType == null) {
/* 172 */             if (existingHandler.getHandlerCodeAddress() != handler.getHandlerCodeAddress()) {
/* 173 */               throw new TryListBuilder.InvalidTryException("Multiple overlapping catch all handlers with different handlers", new Object[0]);
/*     */             }
/*     */ 
/* 176 */             return;
/*     */           }
/* 178 */         } else if (existingType.equals(newType)) {
/* 179 */           if (existingHandler.getHandlerCodeAddress() != handler.getHandlerCodeAddress()) {
/* 180 */             throw new TryListBuilder.InvalidTryException("Multiple overlapping catches for %s with different handlers", new Object[] { existingType });
/*     */           }
/*     */ 
/* 183 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 187 */       this.exceptionHandlers.add(handler);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class InvalidTryException extends ExceptionWithContext
/*     */   {
/*     */     public InvalidTryException(String message, Object[] formatArgs)
/*     */     {
/*  95 */       super(formatArgs);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class TryBounds<EH extends ExceptionHandler>
/*     */   {
/*     */     public final TryListBuilder.MutableTryBlock<EH> start;
/*     */     public final TryListBuilder.MutableTryBlock<EH> end;
/*     */ 
/*     */     public TryBounds(TryListBuilder.MutableTryBlock<EH> start, TryListBuilder.MutableTryBlock<EH> end)
/*     */     {
/*  80 */       this.start = start;
/*  81 */       this.end = end;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.util.TryListBuilder
 * JD-Core Version:    0.6.0
 */