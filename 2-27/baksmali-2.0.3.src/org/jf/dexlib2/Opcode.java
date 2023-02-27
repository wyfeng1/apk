/*     */ package org.jf.dexlib2;
/*     */ 
/*     */ public enum Opcode
/*     */ {
/*  36 */   NOP(0, "nop", 4, Format.Format10x, 4), 
/*  37 */   MOVE(1, "move", 4, Format.Format12x, 20), 
/*  38 */   MOVE_FROM16(2, "move/from16", 4, Format.Format22x, 20), 
/*  39 */   MOVE_16(3, "move/16", 4, Format.Format32x, 20), 
/*  40 */   MOVE_WIDE(4, "move-wide", 4, Format.Format12x, 52), 
/*  41 */   MOVE_WIDE_FROM16(5, "move-wide/from16", 4, Format.Format22x, 52), 
/*  42 */   MOVE_WIDE_16(6, "move-wide/16", 4, Format.Format32x, 52), 
/*  43 */   MOVE_OBJECT(7, "move-object", 4, Format.Format12x, 20), 
/*  44 */   MOVE_OBJECT_FROM16(8, "move-object/from16", 4, Format.Format22x, 20), 
/*  45 */   MOVE_OBJECT_16(9, "move-object/16", 4, Format.Format32x, 20), 
/*  46 */   MOVE_RESULT(10, "move-result", 4, Format.Format11x, 20), 
/*  47 */   MOVE_RESULT_WIDE(11, "move-result-wide", 4, Format.Format11x, 52), 
/*  48 */   MOVE_RESULT_OBJECT(12, "move-result-object", 4, Format.Format11x, 20), 
/*  49 */   MOVE_EXCEPTION(13, "move-exception", 4, Format.Format11x, 20), 
/*  50 */   RETURN_VOID(14, "return-void", 4, Format.Format10x), 
/*  51 */   RETURN(15, "return", 4, Format.Format11x), 
/*  52 */   RETURN_WIDE(16, "return-wide", 4, Format.Format11x), 
/*  53 */   RETURN_OBJECT(17, "return-object", 4, Format.Format11x), 
/*  54 */   CONST_4(18, "const/4", 4, Format.Format11n, 20), 
/*  55 */   CONST_16(19, "const/16", 4, Format.Format21s, 20), 
/*  56 */   CONST(20, "const", 4, Format.Format31i, 20), 
/*  57 */   CONST_HIGH16(21, "const/high16", 4, Format.Format21ih, 20), 
/*  58 */   CONST_WIDE_16(22, "const-wide/16", 4, Format.Format21s, 52), 
/*  59 */   CONST_WIDE_32(23, "const-wide/32", 4, Format.Format31i, 52), 
/*  60 */   CONST_WIDE(24, "const-wide", 4, Format.Format51l, 52), 
/*  61 */   CONST_WIDE_HIGH16(25, "const-wide/high16", 4, Format.Format21lh, 52), 
/*  62 */   CONST_STRING(26, "const-string", 0, Format.Format21c, 21, 27), 
/*  63 */   CONST_STRING_JUMBO(27, "const-string/jumbo", 0, Format.Format31c, 21), 
/*  64 */   CONST_CLASS(28, "const-class", 1, Format.Format21c, 21), 
/*  65 */   MONITOR_ENTER(29, "monitor-enter", 4, Format.Format11x, 5), 
/*  66 */   MONITOR_EXIT(30, "monitor-exit", 4, Format.Format11x, 5), 
/*  67 */   CHECK_CAST(31, "check-cast", 1, Format.Format21c, 21), 
/*  68 */   INSTANCE_OF(32, "instance-of", 1, Format.Format22c, 21), 
/*  69 */   ARRAY_LENGTH(33, "array-length", 4, Format.Format12x, 21), 
/*  70 */   NEW_INSTANCE(34, "new-instance", 1, Format.Format21c, 21), 
/*  71 */   NEW_ARRAY(35, "new-array", 1, Format.Format22c, 21), 
/*  72 */   FILLED_NEW_ARRAY(36, "filled-new-array", 1, Format.Format35c, 13), 
/*  73 */   FILLED_NEW_ARRAY_RANGE(37, "filled-new-array/range", 1, Format.Format3rc, 13), 
/*  74 */   FILL_ARRAY_DATA(38, "fill-array-data", 4, Format.Format31t, 4), 
/*  75 */   THROW(39, "throw", 4, Format.Format11x, 1), 
/*  76 */   GOTO(40, "goto", 4, Format.Format10t), 
/*  77 */   GOTO_16(41, "goto/16", 4, Format.Format20t), 
/*  78 */   GOTO_32(42, "goto/32", 4, Format.Format30t), 
/*  79 */   PACKED_SWITCH(43, "packed-switch", 4, Format.Format31t, 4), 
/*  80 */   SPARSE_SWITCH(44, "sparse-switch", 4, Format.Format31t, 4), 
/*  81 */   CMPL_FLOAT(45, "cmpl-float", 4, Format.Format23x, 20), 
/*  82 */   CMPG_FLOAT(46, "cmpg-float", 4, Format.Format23x, 20), 
/*  83 */   CMPL_DOUBLE(47, "cmpl-double", 4, Format.Format23x, 20), 
/*  84 */   CMPG_DOUBLE(48, "cmpg-double", 4, Format.Format23x, 20), 
/*  85 */   CMP_LONG(49, "cmp-long", 4, Format.Format23x, 20), 
/*  86 */   IF_EQ(50, "if-eq", 4, Format.Format22t, 4), 
/*  87 */   IF_NE(51, "if-ne", 4, Format.Format22t, 4), 
/*  88 */   IF_LT(52, "if-lt", 4, Format.Format22t, 4), 
/*  89 */   IF_GE(53, "if-ge", 4, Format.Format22t, 4), 
/*  90 */   IF_GT(54, "if-gt", 4, Format.Format22t, 4), 
/*  91 */   IF_LE(55, "if-le", 4, Format.Format22t, 4), 
/*  92 */   IF_EQZ(56, "if-eqz", 4, Format.Format21t, 4), 
/*  93 */   IF_NEZ(57, "if-nez", 4, Format.Format21t, 4), 
/*  94 */   IF_LTZ(58, "if-ltz", 4, Format.Format21t, 4), 
/*  95 */   IF_GEZ(59, "if-gez", 4, Format.Format21t, 4), 
/*  96 */   IF_GTZ(60, "if-gtz", 4, Format.Format21t, 4), 
/*  97 */   IF_LEZ(61, "if-lez", 4, Format.Format21t, 4), 
/*  98 */   AGET(68, "aget", 4, Format.Format23x, 21), 
/*  99 */   AGET_WIDE(69, "aget-wide", 4, Format.Format23x, 53), 
/* 100 */   AGET_OBJECT(70, "aget-object", 4, Format.Format23x, 21), 
/* 101 */   AGET_BOOLEAN(71, "aget-boolean", 4, Format.Format23x, 21), 
/* 102 */   AGET_BYTE(72, "aget-byte", 4, Format.Format23x, 21), 
/* 103 */   AGET_CHAR(73, "aget-char", 4, Format.Format23x, 21), 
/* 104 */   AGET_SHORT(74, "aget-short", 4, Format.Format23x, 21), 
/* 105 */   APUT(75, "aput", 4, Format.Format23x, 5), 
/* 106 */   APUT_WIDE(76, "aput-wide", 4, Format.Format23x, 5), 
/* 107 */   APUT_OBJECT(77, "aput-object", 4, Format.Format23x, 5), 
/* 108 */   APUT_BOOLEAN(78, "aput-boolean", 4, Format.Format23x, 5), 
/* 109 */   APUT_BYTE(79, "aput-byte", 4, Format.Format23x, 5), 
/* 110 */   APUT_CHAR(80, "aput-char", 4, Format.Format23x, 5), 
/* 111 */   APUT_SHORT(81, "aput-short", 4, Format.Format23x, 5), 
/* 112 */   IGET(82, "iget", 2, Format.Format22c, 21), 
/* 113 */   IGET_WIDE(83, "iget-wide", 2, Format.Format22c, 53), 
/* 114 */   IGET_OBJECT(84, "iget-object", 2, Format.Format22c, 21), 
/* 115 */   IGET_BOOLEAN(85, "iget-boolean", 2, Format.Format22c, 21), 
/* 116 */   IGET_BYTE(86, "iget-byte", 2, Format.Format22c, 21), 
/* 117 */   IGET_CHAR(87, "iget-char", 2, Format.Format22c, 21), 
/* 118 */   IGET_SHORT(88, "iget-short", 2, Format.Format22c, 21), 
/* 119 */   IPUT(89, "iput", 2, Format.Format22c, 5), 
/* 120 */   IPUT_WIDE(90, "iput-wide", 2, Format.Format22c, 5), 
/* 121 */   IPUT_OBJECT(91, "iput-object", 2, Format.Format22c, 5), 
/* 122 */   IPUT_BOOLEAN(92, "iput-boolean", 2, Format.Format22c, 5), 
/* 123 */   IPUT_BYTE(93, "iput-byte", 2, Format.Format22c, 5), 
/* 124 */   IPUT_CHAR(94, "iput-char", 2, Format.Format22c, 5), 
/* 125 */   IPUT_SHORT(95, "iput-short", 2, Format.Format22c, 5), 
/* 126 */   SGET(96, "sget", 2, Format.Format21c, 21), 
/* 127 */   SGET_WIDE(97, "sget-wide", 2, Format.Format21c, 53), 
/* 128 */   SGET_OBJECT(98, "sget-object", 2, Format.Format21c, 21), 
/* 129 */   SGET_BOOLEAN(99, "sget-boolean", 2, Format.Format21c, 21), 
/* 130 */   SGET_BYTE(100, "sget-byte", 2, Format.Format21c, 21), 
/* 131 */   SGET_CHAR(101, "sget-char", 2, Format.Format21c, 21), 
/* 132 */   SGET_SHORT(102, "sget-short", 2, Format.Format21c, 21), 
/* 133 */   SPUT(103, "sput", 2, Format.Format21c, 5), 
/* 134 */   SPUT_WIDE(104, "sput-wide", 2, Format.Format21c, 5), 
/* 135 */   SPUT_OBJECT(105, "sput-object", 2, Format.Format21c, 5), 
/* 136 */   SPUT_BOOLEAN(106, "sput-boolean", 2, Format.Format21c, 5), 
/* 137 */   SPUT_BYTE(107, "sput-byte", 2, Format.Format21c, 5), 
/* 138 */   SPUT_CHAR(108, "sput-char", 2, Format.Format21c, 5), 
/* 139 */   SPUT_SHORT(109, "sput-short", 2, Format.Format21c, 5), 
/* 140 */   INVOKE_VIRTUAL(110, "invoke-virtual", 3, Format.Format35c, 13), 
/* 141 */   INVOKE_SUPER(111, "invoke-super", 3, Format.Format35c, 13), 
/* 142 */   INVOKE_DIRECT(112, "invoke-direct", 3, Format.Format35c, 1037), 
/* 143 */   INVOKE_STATIC(113, "invoke-static", 3, Format.Format35c, 13), 
/* 144 */   INVOKE_INTERFACE(114, "invoke-interface", 3, Format.Format35c, 13), 
/* 145 */   INVOKE_VIRTUAL_RANGE(116, "invoke-virtual/range", 3, Format.Format3rc, 13), 
/* 146 */   INVOKE_SUPER_RANGE(117, "invoke-super/range", 3, Format.Format3rc, 13), 
/* 147 */   INVOKE_DIRECT_RANGE(118, "invoke-direct/range", 3, Format.Format3rc, 1037), 
/* 148 */   INVOKE_STATIC_RANGE(119, "invoke-static/range", 3, Format.Format3rc, 13), 
/* 149 */   INVOKE_INTERFACE_RANGE(120, "invoke-interface/range", 3, Format.Format3rc, 13), 
/* 150 */   NEG_INT(123, "neg-int", 4, Format.Format12x, 20), 
/* 151 */   NOT_INT(124, "not-int", 4, Format.Format12x, 20), 
/* 152 */   NEG_LONG(125, "neg-long", 4, Format.Format12x, 52), 
/* 153 */   NOT_LONG(126, "not-long", 4, Format.Format12x, 52), 
/* 154 */   NEG_FLOAT(127, "neg-float", 4, Format.Format12x, 20), 
/* 155 */   NEG_DOUBLE(128, "neg-double", 4, Format.Format12x, 52), 
/* 156 */   INT_TO_LONG(129, "int-to-long", 4, Format.Format12x, 52), 
/* 157 */   INT_TO_FLOAT(130, "int-to-float", 4, Format.Format12x, 20), 
/* 158 */   INT_TO_DOUBLE(131, "int-to-double", 4, Format.Format12x, 52), 
/* 159 */   LONG_TO_INT(132, "long-to-int", 4, Format.Format12x, 20), 
/* 160 */   LONG_TO_FLOAT(133, "long-to-float", 4, Format.Format12x, 20), 
/* 161 */   LONG_TO_DOUBLE(134, "long-to-double", 4, Format.Format12x, 52), 
/* 162 */   FLOAT_TO_INT(135, "float-to-int", 4, Format.Format12x, 20), 
/* 163 */   FLOAT_TO_LONG(136, "float-to-long", 4, Format.Format12x, 52), 
/* 164 */   FLOAT_TO_DOUBLE(137, "float-to-double", 4, Format.Format12x, 52), 
/* 165 */   DOUBLE_TO_INT(138, "double-to-int", 4, Format.Format12x, 20), 
/* 166 */   DOUBLE_TO_LONG(139, "double-to-long", 4, Format.Format12x, 52), 
/* 167 */   DOUBLE_TO_FLOAT(140, "double-to-float", 4, Format.Format12x, 20), 
/* 168 */   INT_TO_BYTE(141, "int-to-byte", 4, Format.Format12x, 20), 
/* 169 */   INT_TO_CHAR(142, "int-to-char", 4, Format.Format12x, 20), 
/* 170 */   INT_TO_SHORT(143, "int-to-short", 4, Format.Format12x, 20), 
/* 171 */   ADD_INT(144, "add-int", 4, Format.Format23x, 20), 
/* 172 */   SUB_INT(145, "sub-int", 4, Format.Format23x, 20), 
/* 173 */   MUL_INT(146, "mul-int", 4, Format.Format23x, 20), 
/* 174 */   DIV_INT(147, "div-int", 4, Format.Format23x, 21), 
/* 175 */   REM_INT(148, "rem-int", 4, Format.Format23x, 21), 
/* 176 */   AND_INT(149, "and-int", 4, Format.Format23x, 20), 
/* 177 */   OR_INT(150, "or-int", 4, Format.Format23x, 20), 
/* 178 */   XOR_INT(151, "xor-int", 4, Format.Format23x, 20), 
/* 179 */   SHL_INT(152, "shl-int", 4, Format.Format23x, 20), 
/* 180 */   SHR_INT(153, "shr-int", 4, Format.Format23x, 20), 
/* 181 */   USHR_INT(154, "ushr-int", 4, Format.Format23x, 20), 
/* 182 */   ADD_LONG(155, "add-long", 4, Format.Format23x, 52), 
/* 183 */   SUB_LONG(156, "sub-long", 4, Format.Format23x, 52), 
/* 184 */   MUL_LONG(157, "mul-long", 4, Format.Format23x, 52), 
/* 185 */   DIV_LONG(158, "div-long", 4, Format.Format23x, 53), 
/* 186 */   REM_LONG(159, "rem-long", 4, Format.Format23x, 53), 
/* 187 */   AND_LONG(160, "and-long", 4, Format.Format23x, 52), 
/* 188 */   OR_LONG(161, "or-long", 4, Format.Format23x, 52), 
/* 189 */   XOR_LONG(162, "xor-long", 4, Format.Format23x, 52), 
/* 190 */   SHL_LONG(163, "shl-long", 4, Format.Format23x, 52), 
/* 191 */   SHR_LONG(164, "shr-long", 4, Format.Format23x, 52), 
/* 192 */   USHR_LONG(165, "ushr-long", 4, Format.Format23x, 52), 
/* 193 */   ADD_FLOAT(166, "add-float", 4, Format.Format23x, 20), 
/* 194 */   SUB_FLOAT(167, "sub-float", 4, Format.Format23x, 20), 
/* 195 */   MUL_FLOAT(168, "mul-float", 4, Format.Format23x, 20), 
/* 196 */   DIV_FLOAT(169, "div-float", 4, Format.Format23x, 20), 
/* 197 */   REM_FLOAT(170, "rem-float", 4, Format.Format23x, 20), 
/* 198 */   ADD_DOUBLE(171, "add-double", 4, Format.Format23x, 52), 
/* 199 */   SUB_DOUBLE(172, "sub-double", 4, Format.Format23x, 52), 
/* 200 */   MUL_DOUBLE(173, "mul-double", 4, Format.Format23x, 52), 
/* 201 */   DIV_DOUBLE(174, "div-double", 4, Format.Format23x, 52), 
/* 202 */   REM_DOUBLE(175, "rem-double", 4, Format.Format23x, 52), 
/* 203 */   ADD_INT_2ADDR(176, "add-int/2addr", 4, Format.Format12x, 20), 
/* 204 */   SUB_INT_2ADDR(177, "sub-int/2addr", 4, Format.Format12x, 20), 
/* 205 */   MUL_INT_2ADDR(178, "mul-int/2addr", 4, Format.Format12x, 20), 
/* 206 */   DIV_INT_2ADDR(179, "div-int/2addr", 4, Format.Format12x, 21), 
/* 207 */   REM_INT_2ADDR(180, "rem-int/2addr", 4, Format.Format12x, 21), 
/* 208 */   AND_INT_2ADDR(181, "and-int/2addr", 4, Format.Format12x, 20), 
/* 209 */   OR_INT_2ADDR(182, "or-int/2addr", 4, Format.Format12x, 20), 
/* 210 */   XOR_INT_2ADDR(183, "xor-int/2addr", 4, Format.Format12x, 20), 
/* 211 */   SHL_INT_2ADDR(184, "shl-int/2addr", 4, Format.Format12x, 20), 
/* 212 */   SHR_INT_2ADDR(185, "shr-int/2addr", 4, Format.Format12x, 20), 
/* 213 */   USHR_INT_2ADDR(186, "ushr-int/2addr", 4, Format.Format12x, 20), 
/* 214 */   ADD_LONG_2ADDR(187, "add-long/2addr", 4, Format.Format12x, 52), 
/* 215 */   SUB_LONG_2ADDR(188, "sub-long/2addr", 4, Format.Format12x, 52), 
/* 216 */   MUL_LONG_2ADDR(189, "mul-long/2addr", 4, Format.Format12x, 52), 
/* 217 */   DIV_LONG_2ADDR(190, "div-long/2addr", 4, Format.Format12x, 53), 
/* 218 */   REM_LONG_2ADDR(191, "rem-long/2addr", 4, Format.Format12x, 53), 
/* 219 */   AND_LONG_2ADDR(192, "and-long/2addr", 4, Format.Format12x, 52), 
/* 220 */   OR_LONG_2ADDR(193, "or-long/2addr", 4, Format.Format12x, 52), 
/* 221 */   XOR_LONG_2ADDR(194, "xor-long/2addr", 4, Format.Format12x, 52), 
/* 222 */   SHL_LONG_2ADDR(195, "shl-long/2addr", 4, Format.Format12x, 52), 
/* 223 */   SHR_LONG_2ADDR(196, "shr-long/2addr", 4, Format.Format12x, 52), 
/* 224 */   USHR_LONG_2ADDR(197, "ushr-long/2addr", 4, Format.Format12x, 52), 
/* 225 */   ADD_FLOAT_2ADDR(198, "add-float/2addr", 4, Format.Format12x, 20), 
/* 226 */   SUB_FLOAT_2ADDR(199, "sub-float/2addr", 4, Format.Format12x, 20), 
/* 227 */   MUL_FLOAT_2ADDR(200, "mul-float/2addr", 4, Format.Format12x, 20), 
/* 228 */   DIV_FLOAT_2ADDR(201, "div-float/2addr", 4, Format.Format12x, 20), 
/* 229 */   REM_FLOAT_2ADDR(202, "rem-float/2addr", 4, Format.Format12x, 20), 
/* 230 */   ADD_DOUBLE_2ADDR(203, "add-double/2addr", 4, Format.Format12x, 52), 
/* 231 */   SUB_DOUBLE_2ADDR(204, "sub-double/2addr", 4, Format.Format12x, 52), 
/* 232 */   MUL_DOUBLE_2ADDR(205, "mul-double/2addr", 4, Format.Format12x, 52), 
/* 233 */   DIV_DOUBLE_2ADDR(206, "div-double/2addr", 4, Format.Format12x, 52), 
/* 234 */   REM_DOUBLE_2ADDR(207, "rem-double/2addr", 4, Format.Format12x, 52), 
/* 235 */   ADD_INT_LIT16(208, "add-int/lit16", 4, Format.Format22s, 20), 
/* 236 */   RSUB_INT(209, "rsub-int", 4, Format.Format22s, 20), 
/* 237 */   MUL_INT_LIT16(210, "mul-int/lit16", 4, Format.Format22s, 20), 
/* 238 */   DIV_INT_LIT16(211, "div-int/lit16", 4, Format.Format22s, 21), 
/* 239 */   REM_INT_LIT16(212, "rem-int/lit16", 4, Format.Format22s, 21), 
/* 240 */   AND_INT_LIT16(213, "and-int/lit16", 4, Format.Format22s, 20), 
/* 241 */   OR_INT_LIT16(214, "or-int/lit16", 4, Format.Format22s, 20), 
/* 242 */   XOR_INT_LIT16(215, "xor-int/lit16", 4, Format.Format22s, 20), 
/* 243 */   ADD_INT_LIT8(216, "add-int/lit8", 4, Format.Format22b, 20), 
/* 244 */   RSUB_INT_LIT8(217, "rsub-int/lit8", 4, Format.Format22b, 20), 
/* 245 */   MUL_INT_LIT8(218, "mul-int/lit8", 4, Format.Format22b, 20), 
/* 246 */   DIV_INT_LIT8(219, "div-int/lit8", 4, Format.Format22b, 21), 
/* 247 */   REM_INT_LIT8(220, "rem-int/lit8", 4, Format.Format22b, 21), 
/* 248 */   AND_INT_LIT8(221, "and-int/lit8", 4, Format.Format22b, 20), 
/* 249 */   OR_INT_LIT8(222, "or-int/lit8", 4, Format.Format22b, 20), 
/* 250 */   XOR_INT_LIT8(223, "xor-int/lit8", 4, Format.Format22b, 20), 
/* 251 */   SHL_INT_LIT8(224, "shl-int/lit8", 4, Format.Format22b, 20), 
/* 252 */   SHR_INT_LIT8(225, "shr-int/lit8", 4, Format.Format22b, 20), 
/* 253 */   USHR_INT_LIT8(226, "ushr-int/lit8", 4, Format.Format22b, 20), 
/*     */ 
/* 255 */   IGET_VOLATILE(227, "iget-volatile", minApi(9), 2, Format.Format22c, 151), 
/* 256 */   IPUT_VOLATILE(228, "iput-volatile", minApi(9), 2, Format.Format22c, 135), 
/* 257 */   SGET_VOLATILE(229, "sget-volatile", minApi(9), 2, Format.Format21c, 279), 
/* 258 */   SPUT_VOLATILE(230, "sput-volatile", minApi(9), 2, Format.Format21c, 263), 
/* 259 */   IGET_OBJECT_VOLATILE(231, "iget-object-volatile", minApi(9), 2, Format.Format22c, 151), 
/* 260 */   IGET_WIDE_VOLATILE(232, "iget-wide-volatile", minApi(9), 2, Format.Format22c, 183), 
/* 261 */   IPUT_WIDE_VOLATILE(233, "iput-wide-volatile", minApi(9), 2, Format.Format22c, 135), 
/* 262 */   SGET_WIDE_VOLATILE(234, "sget-wide-volatile", minApi(9), 2, Format.Format21c, 311), 
/* 263 */   SPUT_WIDE_VOLATILE(235, "sput-wide-volatile", minApi(9), 2, Format.Format21c, 263), 
/*     */ 
/* 265 */   THROW_VERIFICATION_ERROR(237, "throw-verification-error", minApi(5), 4, Format.Format20bc, 3), 
/* 266 */   EXECUTE_INLINE(238, "execute-inline", 4, Format.Format35mi, 15), 
/* 267 */   EXECUTE_INLINE_RANGE(239, "execute-inline/range", minApi(8), 4, Format.Format3rmi, 15), 
/* 268 */   INVOKE_DIRECT_EMPTY(240, "invoke-direct-empty", maxApi(13), 3, Format.Format35c, 1039), 
/* 269 */   INVOKE_OBJECT_INIT_RANGE(240, "invoke-object-init/range", minApi(14), 3, Format.Format3rc, 1039), 
/* 270 */   RETURN_VOID_BARRIER(241, "return-void-barrier", minApi(11), 4, Format.Format10x, 2), 
/* 271 */   IGET_QUICK(242, "iget-quick", 4, Format.Format22cs, 87), 
/* 272 */   IGET_WIDE_QUICK(243, "iget-wide-quick", 4, Format.Format22cs, 119), 
/* 273 */   IGET_OBJECT_QUICK(244, "iget-object-quick", 4, Format.Format22cs, 87), 
/* 274 */   IPUT_QUICK(245, "iput-quick", 4, Format.Format22cs, 71), 
/* 275 */   IPUT_WIDE_QUICK(246, "iput-wide-quick", 4, Format.Format22cs, 71), 
/* 276 */   IPUT_OBJECT_QUICK(247, "iput-object-quick", 4, Format.Format22cs, 71), 
/* 277 */   INVOKE_VIRTUAL_QUICK(248, "invoke-virtual-quick", 4, Format.Format35ms, 15), 
/* 278 */   INVOKE_VIRTUAL_QUICK_RANGE(249, "invoke-virtual-quick/range", 4, Format.Format3rms, 15), 
/* 279 */   INVOKE_SUPER_QUICK(250, "invoke-super-quick", 4, Format.Format35ms, 15), 
/* 280 */   INVOKE_SUPER_QUICK_RANGE(251, "invoke-super-quick/range", 4, Format.Format3rms, 15), 
/*     */ 
/* 282 */   IPUT_OBJECT_VOLATILE(252, "iput-object-volatile", minApi(9), 2, Format.Format22c, 135), 
/* 283 */   SGET_OBJECT_VOLATILE(253, "sget-object-volatile", minApi(9), 2, Format.Format21c, 279), 
/* 284 */   SPUT_OBJECT_VOLATILE(254, "sput-object-volatile", minApi(9), 2, Format.Format21c, 263), 
/*     */ 
/* 286 */   PACKED_SWITCH_PAYLOAD(256, "packed-switch-payload", 4, Format.PackedSwitchPayload, 0), 
/* 287 */   SPARSE_SWITCH_PAYLOAD(512, "sparse-switch-payload", 4, Format.SparseSwitchPayload, 0), 
/* 288 */   ARRAY_PAYLOAD(768, "array-payload", 4, Format.ArrayPayload, 0);
/*     */ 
/*     */   public final short value;
/*     */   public final String name;
/*     */   public final int apiConstraints;
/*     */   public final int referenceType;
/*     */   public final Format format;
/*     */   public final int flags;
/*     */ 
/*     */   private static int minApi(int api)
/*     */   {
/* 316 */     return 0xFFFF0000 | api & 0xFFFF;
/*     */   }
/*     */ 
/*     */   private static int maxApi(int api) {
/* 320 */     return api << 16;
/*     */   }
/*     */ 
/*     */   private Opcode(short opcodeValue, String opcodeName, int referenceType, Format format)
/*     */   {
/* 332 */     this(opcodeValue, opcodeName, -65536, referenceType, format, 0, -1);
/*     */   }
/*     */ 
/*     */   private Opcode(short opcodeValue, String opcodeName, int referenceType, Format format, int flags) {
/* 336 */     this(opcodeValue, opcodeName, -65536, referenceType, format, flags, -1);
/*     */   }
/*     */ 
/*     */   private Opcode(short opcodeValue, String opcodeName, int referenceType, Format format, int flags, short jumboOpcodeValue) {
/* 340 */     this(opcodeValue, opcodeName, -65536, referenceType, format, flags, jumboOpcodeValue);
/*     */   }
/*     */ 
/*     */   private Opcode(short opcodeValue, String opcodeName, int apiConstraints, int referenceType, Format format, int flags)
/*     */   {
/* 348 */     this(opcodeValue, opcodeName, apiConstraints, referenceType, format, flags, -1);
/*     */   }
/*     */ 
/*     */   private Opcode(short opcodeValue, String opcodeName, int apiConstraints, int referenceType, Format format, int flags, short jumboOpcodeValue)
/*     */   {
/* 353 */     this.value = opcodeValue;
/* 354 */     this.name = opcodeName;
/* 355 */     this.apiConstraints = apiConstraints;
/* 356 */     this.referenceType = referenceType;
/* 357 */     this.format = format;
/* 358 */     this.flags = flags;
/*     */   }
/*     */ 
/*     */   public int getMinApi()
/*     */   {
/* 367 */     return this.apiConstraints & 0xFFFF;
/*     */   }
/*     */ 
/*     */   public int getMaxApi()
/*     */   {
/* 374 */     return this.apiConstraints >>> 16;
/*     */   }
/*     */ 
/*     */   public final boolean canThrow() {
/* 378 */     return (this.flags & 0x1) != 0;
/*     */   }
/*     */ 
/*     */   public final boolean odexOnly() {
/* 382 */     return (this.flags & 0x2) != 0;
/*     */   }
/*     */ 
/*     */   public final boolean canContinue() {
/* 386 */     return (this.flags & 0x4) != 0;
/*     */   }
/*     */ 
/*     */   public final boolean setsResult() {
/* 390 */     return (this.flags & 0x8) != 0;
/*     */   }
/*     */ 
/*     */   public final boolean setsRegister() {
/* 394 */     return (this.flags & 0x10) != 0;
/*     */   }
/*     */ 
/*     */   public final boolean setsWideRegister() {
/* 398 */     return (this.flags & 0x20) != 0;
/*     */   }
/*     */ 
/*     */   public final boolean isOdexedInstanceQuick() {
/* 402 */     return (this.flags & 0x40) != 0;
/*     */   }
/*     */ 
/*     */   public final boolean isOdexedInstanceVolatile() {
/* 406 */     return (this.flags & 0x80) != 0;
/*     */   }
/*     */ 
/*     */   public final boolean isOdexedStaticVolatile() {
/* 410 */     return (this.flags & 0x100) != 0;
/*     */   }
/*     */ 
/*     */   public final boolean canInitializeReference()
/*     */   {
/* 418 */     return (this.flags & 0x400) != 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.Opcode
 * JD-Core Version:    0.6.0
 */