/*     */ package org.jf.dexlib2.util;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.jf.dexlib2.Opcode;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.WideLiteralInstruction;
/*     */ 
/*     */ public class SyntheticAccessorFSM
/*     */ {
/*  59 */   private static final byte[] _SyntheticAccessorFSM_actions = init__SyntheticAccessorFSM_actions_0();
/*     */ 
/*  70 */   private static final short[] _SyntheticAccessorFSM_key_offsets = init__SyntheticAccessorFSM_key_offsets_0();
/*     */ 
/*  96 */   private static final short[] _SyntheticAccessorFSM_trans_keys = init__SyntheticAccessorFSM_trans_keys_0();
/*     */ 
/* 107 */   private static final byte[] _SyntheticAccessorFSM_single_lengths = init__SyntheticAccessorFSM_single_lengths_0();
/*     */ 
/* 118 */   private static final byte[] _SyntheticAccessorFSM_range_lengths = init__SyntheticAccessorFSM_range_lengths_0();
/*     */ 
/* 129 */   private static final short[] _SyntheticAccessorFSM_index_offsets = init__SyntheticAccessorFSM_index_offsets_0();
/*     */ 
/* 154 */   private static final byte[] _SyntheticAccessorFSM_indicies = init__SyntheticAccessorFSM_indicies_0();
/*     */ 
/* 167 */   private static final byte[] _SyntheticAccessorFSM_trans_targs = init__SyntheticAccessorFSM_trans_targs_0();
/*     */ 
/* 180 */   private static final byte[] _SyntheticAccessorFSM_trans_actions = init__SyntheticAccessorFSM_trans_actions_0();
/*     */ 
/*     */   private static byte[] init__SyntheticAccessorFSM_actions_0()
/*     */   {
/*  49 */     return new byte[] { 0, 1, 0, 1, 1, 1, 2, 1, 13, 1, 14, 1, 15, 1, 16, 1, 17, 1, 18, 1, 19, 1, 20, 1, 21, 1, 25, 2, 3, 7, 2, 4, 7, 2, 5, 7, 2, 6, 7, 2, 8, 12, 2, 9, 12, 2, 10, 12, 2, 11, 12, 2, 22, 23, 2, 22, 24, 2, 22, 25, 2, 22, 26, 2, 22, 27, 2, 22, 28 };
/*     */   }
/*     */ 
/*     */   private static short[] init__SyntheticAccessorFSM_key_offsets_0()
/*     */   {
/*  64 */     return new short[] { 0, 0, 12, 82, 98, 102, 104, 166, 172, 174, 180, 184, 190, 192, 196, 198, 201, 203 };
/*     */   }
/*     */ 
/*     */   private static short[] init__SyntheticAccessorFSM_trans_keys_0()
/*     */   {
/*  75 */     return new short[] { 82, 88, 89, 95, 96, 102, 103, 109, 110, 114, 116, 120, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 177, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 201, 202, 203, 204, 206, 207, 208, 216, 15, 17, 18, 25, 129, 143, 144, 176, 178, 205, 144, 145, 155, 156, 166, 167, 171, 172, 176, 177, 187, 188, 198, 199, 203, 204, 89, 95, 103, 109, 15, 17, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 177, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 201, 202, 203, 204, 206, 207, 144, 176, 178, 205, 89, 95, 103, 109, 129, 143, 15, 17, 89, 95, 103, 109, 129, 143, 89, 95, 103, 109, 89, 95, 103, 109, 129, 143, 15, 17, 89, 95, 103, 109, 15, 17, 14, 10, 12, 15, 17, 0 };
/*     */   }
/*     */ 
/*     */   private static byte[] init__SyntheticAccessorFSM_single_lengths_0()
/*     */   {
/* 101 */     return new byte[] { 0, 0, 60, 16, 0, 0, 58, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 };
/*     */   }
/*     */ 
/*     */   private static byte[] init__SyntheticAccessorFSM_range_lengths_0()
/*     */   {
/* 112 */     return new byte[] { 0, 6, 5, 0, 2, 1, 2, 3, 1, 3, 2, 3, 1, 2, 1, 1, 1, 0 };
/*     */   }
/*     */ 
/*     */   private static short[] init__SyntheticAccessorFSM_index_offsets_0()
/*     */   {
/* 123 */     return new short[] { 0, 0, 7, 73, 90, 93, 95, 156, 160, 162, 166, 169, 173, 175, 178, 180, 183, 185 };
/*     */   }
/*     */ 
/*     */   private static byte[] init__SyntheticAccessorFSM_indicies_0()
/*     */   {
/* 134 */     return new byte[] { 0, 2, 0, 2, 3, 3, 1, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 9, 10, 11, 12, 13, 14, 15, 16, 17, 20, 21, 9, 10, 11, 22, 23, 9, 10, 11, 8, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 10, 11, 12, 13, 14, 15, 16, 17, 20, 21, 10, 11, 22, 23, 10, 11, 24, 24, 4, 5, 6, 7, 9, 1, 25, 26, 27, 28, 29, 30, 31, 32, 25, 26, 27, 28, 29, 30, 31, 32, 1, 33, 33, 1, 34, 1, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 9, 10, 11, 12, 13, 14, 15, 16, 17, 20, 21, 9, 10, 11, 22, 23, 9, 10, 11, 8, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 10, 11, 12, 13, 14, 15, 16, 17, 20, 21, 10, 11, 22, 23, 10, 11, 7, 9, 1, 35, 35, 36, 1, 37, 1, 35, 35, 38, 1, 35, 35, 1, 39, 39, 40, 1, 41, 1, 39, 39, 1, 42, 1, 44, 43, 1, 45, 1, 1, 0 };
/*     */   }
/*     */ 
/*     */   private static byte[] init__SyntheticAccessorFSM_trans_targs_0()
/*     */   {
/* 159 */     return new byte[] { 2, 0, 14, 15, 17, 3, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 11, 4, 4, 4, 4, 4, 4, 4, 4, 5, 17, 8, 9, 17, 10, 12, 13, 17, 17, 16, 17, 17 };
/*     */   }
/*     */ 
/*     */   private static byte[] init__SyntheticAccessorFSM_trans_actions_0()
/*     */   {
/* 172 */     return new byte[] { 0, 0, 1, 0, 51, 3, 0, 27, 39, 7, 9, 11, 13, 15, 17, 19, 21, 23, 30, 42, 33, 45, 36, 48, 5, 27, 39, 30, 42, 33, 45, 36, 48, 1, 63, 1, 0, 66, 0, 1, 0, 60, 54, 0, 25, 57 };
/*     */   }
/*     */ 
/*     */   public static int test(List<? extends Instruction> instructions)
/*     */   {
/* 215 */     int accessorType = -1;
/* 216 */     int p = 0;
/* 217 */     int pe = instructions.size();
/*     */ 
/* 220 */     int mathOp = -1;
/*     */ 
/* 223 */     int mathType = -1;
/*     */ 
/* 226 */     long constantValue = 0L;
/*     */ 
/* 229 */     int putRegister = -1;
/*     */ 
/* 231 */     int returnRegister = -1;
/*     */ 
/* 236 */     int cs = 1;
/*     */ 
/* 242 */     int _trans = 0;
/*     */ 
/* 246 */     int _goto_targ = 0;
/*     */     while (true)
/*     */     {
/* 249 */       switch (_goto_targ) {
/*     */       case 0:
/* 251 */         if (p == pe) {
/* 252 */           _goto_targ = 4;
/* 253 */           continue;
/*     */         }
/* 255 */         if (cs == 0)
/* 256 */           _goto_targ = 5;
/* 257 */         break;
/*     */       case 1:
/* 261 */         int _keys = _SyntheticAccessorFSM_key_offsets[cs];
/* 262 */         _trans = _SyntheticAccessorFSM_index_offsets[cs];
/* 263 */         int _klen = _SyntheticAccessorFSM_single_lengths[cs];
/* 264 */         if (_klen > 0) {
/* 265 */           int _lower = _keys;
/*     */ 
/* 267 */           int _upper = _keys + _klen - 1;
/*     */ 
/* 269 */           while (_upper >= _lower)
/*     */           {
/* 272 */             int _mid = _lower + (_upper - _lower >> 1);
/* 273 */             if (((Instruction)instructions.get(p)).getOpcode().value < _SyntheticAccessorFSM_trans_keys[_mid]) {
/* 274 */               _upper = _mid - 1; continue;
/* 275 */             }if (((Instruction)instructions.get(p)).getOpcode().value > _SyntheticAccessorFSM_trans_keys[_mid]) {
/* 276 */               _lower = _mid + 1; continue;
/*     */             }
/* 278 */             _trans += _mid - _keys;
/* 279 */             break label406;
/*     */           }
/*     */ 
/* 282 */           _keys += _klen;
/* 283 */           _trans += _klen;
/*     */         }
/*     */ 
/* 286 */         _klen = _SyntheticAccessorFSM_range_lengths[cs];
/* 287 */         if (_klen > 0) {
/* 288 */           int _lower = _keys;
/*     */ 
/* 290 */           int _upper = _keys + (_klen << 1) - 2;
/*     */ 
/* 292 */           while (_upper >= _lower)
/*     */           {
/* 295 */             int _mid = _lower + (_upper - _lower >> 1 & 0xFFFFFFFE);
/* 296 */             if (((Instruction)instructions.get(p)).getOpcode().value < _SyntheticAccessorFSM_trans_keys[_mid]) {
/* 297 */               _upper = _mid - 2; continue;
/* 298 */             }if (((Instruction)instructions.get(p)).getOpcode().value > _SyntheticAccessorFSM_trans_keys[(_mid + 1)]) {
/* 299 */               _lower = _mid + 2; continue;
/*     */             }
/* 301 */             _trans += (_mid - _keys >> 1);
/* 302 */             break label406;
/*     */           }
/*     */ 
/* 305 */           _trans += _klen;
/*     */         }
/*     */ 
/* 309 */         _trans = _SyntheticAccessorFSM_indicies[_trans];
/* 310 */         cs = _SyntheticAccessorFSM_trans_targs[_trans];
/*     */ 
/* 312 */         if (_SyntheticAccessorFSM_trans_actions[_trans] != 0) {
/* 313 */           int _acts = _SyntheticAccessorFSM_trans_actions[_trans];
/* 314 */           int _nacts = _SyntheticAccessorFSM_actions[(_acts++)];
/*     */           while (true) { if (_nacts-- <= 0) break label890;
/* 317 */             switch (_SyntheticAccessorFSM_actions[(_acts++)])
/*     */             {
/*     */             case 0:
/* 322 */               putRegister = ((OneRegisterInstruction)instructions.get(p)).getRegisterA();
/*     */ 
/* 324 */               break;
/*     */             case 1:
/* 328 */               constantValue = ((WideLiteralInstruction)instructions.get(p)).getWideLiteral();
/*     */ 
/* 330 */               break;
/*     */             case 2:
/* 334 */               mathType = 0;
/* 335 */               mathOp = 7;
/* 336 */               constantValue = ((WideLiteralInstruction)instructions.get(p)).getWideLiteral();
/*     */ 
/* 338 */               break;
/*     */             case 3:
/* 341 */               mathType = 0;
/* 342 */               break;
/*     */             case 4:
/* 345 */               mathType = 1;
/* 346 */               break;
/*     */             case 5:
/* 349 */               mathType = 2;
/* 350 */               break;
/*     */             case 6:
/* 353 */               mathType = 3;
/* 354 */               break;
/*     */             case 7:
/* 358 */               mathOp = 7;
/*     */ 
/* 360 */               break;
/*     */             case 8:
/* 363 */               mathType = 0;
/* 364 */               break;
/*     */             case 9:
/* 367 */               mathType = 1;
/* 368 */               break;
/*     */             case 10:
/* 371 */               mathType = 2;
/* 372 */               break;
/*     */             case 11:
/* 375 */               mathType = 3;
/* 376 */               break;
/*     */             case 12:
/* 380 */               mathOp = 8;
/*     */ 
/* 382 */               break;
/*     */             case 13:
/* 386 */               mathOp = 9;
/*     */ 
/* 388 */               break;
/*     */             case 14:
/* 392 */               mathOp = 10;
/*     */ 
/* 394 */               break;
/*     */             case 15:
/* 398 */               mathOp = 11;
/*     */ 
/* 400 */               break;
/*     */             case 16:
/* 404 */               mathOp = 12;
/*     */ 
/* 406 */               break;
/*     */             case 17:
/* 410 */               mathOp = 13;
/*     */ 
/* 412 */               break;
/*     */             case 18:
/* 416 */               mathOp = 14;
/*     */ 
/* 418 */               break;
/*     */             case 19:
/* 422 */               mathOp = 15;
/*     */ 
/* 424 */               break;
/*     */             case 20:
/* 428 */               mathOp = 16;
/*     */ 
/* 430 */               break;
/*     */             case 21:
/* 434 */               mathOp = 17;
/*     */ 
/* 436 */               break;
/*     */             case 22:
/* 440 */               returnRegister = ((OneRegisterInstruction)instructions.get(p)).getRegisterA();
/*     */ 
/* 442 */               break;
/*     */             case 23:
/* 446 */               accessorType = 1; p++; _goto_targ = 5; break;
/*     */             case 24:
/* 452 */               accessorType = 2; p++; _goto_targ = 5; break;
/*     */             case 25:
/* 458 */               accessorType = 0; p++; _goto_targ = 5; break;
/*     */             case 26:
/* 464 */               accessorType = getIncrementType(mathOp, mathType, constantValue, putRegister, returnRegister);
/*     */ 
/* 466 */               break;
/*     */             case 27:
/* 470 */               accessorType = getIncrementType(mathOp, mathType, constantValue, putRegister, returnRegister);
/*     */ 
/* 472 */               break;
/*     */             case 28:
/* 476 */               accessorType = mathOp; p++; _goto_targ = 5; break;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       case 2:
/* 485 */         label406: label890: if (cs == 0) {
/* 486 */           _goto_targ = 5;
/* 487 */           continue;
/*     */         }
/* 489 */         p++; if (p == pe) break label915; _goto_targ = 1;
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 502 */     label915: return accessorType;
/*     */   }
/*     */ 
/*     */   private static int getIncrementType(int mathOp, int mathType, long constantValue, int putRegister, int returnRegister)
/*     */   {
/* 507 */     boolean isPrefix = putRegister == returnRegister;
/*     */ 
/* 509 */     boolean negativeConstant = false;
/*     */ 
/* 511 */     switch (mathType) {
/*     */     case 0:
/*     */     case 1:
/* 514 */       if (constantValue == 1L)
/* 515 */         negativeConstant = false;
/* 516 */       else if (constantValue == -1L)
/* 517 */         negativeConstant = true;
/*     */       else {
/* 519 */         return -1;
/*     */       }
/*     */ 
/*     */     case 2:
/* 524 */       float val = Float.intBitsToFloat((int)constantValue);
/* 525 */       if (val == 1.0F)
/* 526 */         negativeConstant = false;
/* 527 */       else if (val == -1.0F)
/* 528 */         negativeConstant = true;
/*     */       else {
/* 530 */         return -1;
/*     */       }
/*     */ 
/*     */     case 3:
/* 535 */       double val = Double.longBitsToDouble(constantValue);
/* 536 */       if (val == 1.0D)
/* 537 */         negativeConstant = false;
/* 538 */       else if (val == -1.0D)
/* 539 */         negativeConstant = true;
/*     */       else {
/* 541 */         return -1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 547 */     boolean isAdd = ((mathOp == 7) && (!negativeConstant)) || ((mathOp == 8) && (negativeConstant));
/*     */ 
/* 550 */     if (isPrefix) {
/* 551 */       if (isAdd) {
/* 552 */         return 4;
/*     */       }
/* 554 */       return 6;
/*     */     }
/*     */ 
/* 557 */     if (isAdd) {
/* 558 */       return 3;
/*     */     }
/* 560 */     return 5;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.SyntheticAccessorFSM
 * JD-Core Version:    0.6.0
 */