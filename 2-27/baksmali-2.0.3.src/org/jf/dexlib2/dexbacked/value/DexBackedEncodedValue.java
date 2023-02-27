/*     */ package org.jf.dexlib2.dexbacked.value;
/*     */ 
/*     */ import org.jf.dexlib2.dexbacked.DexReader;
/*     */ import org.jf.dexlib2.iface.value.EncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableBooleanEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableByteEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableCharEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableDoubleEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableFloatEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableIntEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableLongEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableNullEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableShortEncodedValue;
/*     */ import org.jf.dexlib2.util.Preconditions;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public abstract class DexBackedEncodedValue
/*     */ {
/*     */   public static EncodedValue readFrom(DexReader reader)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual 33	org/jf/dexlib2/dexbacked/DexReader:getOffset	()I
/*     */     //   4: istore_1
/*     */     //   5: aload_0
/*     */     //   6: invokevirtual 41	org/jf/dexlib2/dexbacked/DexReader:readUbyte	()I
/*     */     //   9: istore_2
/*     */     //   10: iload_2
/*     */     //   11: bipush 31
/*     */     //   13: iand
/*     */     //   14: istore_3
/*     */     //   15: iload_2
/*     */     //   16: iconst_5
/*     */     //   17: iushr
/*     */     //   18: istore 4
/*     */     //   20: iload_3
/*     */     //   21: tableswitch	default:+450 -> 471, 0:+143->164, 1:+450->471, 2:+162->183, 3:+185->206, 4:+208->229, 5:+450->471, 6:+230->251, 7:+450->471, 8:+450->471, 9:+450->471, 10:+450->471, 11:+450->471, 12:+450->471, 13:+450->471, 14:+450->471, 15:+450->471, 16:+253->274, 17:+278->299, 18:+450->471, 19:+450->471, 20:+450->471, 21:+450->471, 22:+450->471, 23:+304->325, 24:+321->342, 25:+338->359, 26:+355->376, 27:+372->393, 28:+389->410, 29:+404->425, 30:+419->440, 31:+429->450
/*     */     //   165: iconst_1
/*     */     //   166: iconst_0
/*     */     //   167: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   170: new 19	org/jf/dexlib2/immutable/value/ImmutableByteEncodedValue
/*     */     //   173: dup
/*     */     //   174: aload_0
/*     */     //   175: invokevirtual 35	org/jf/dexlib2/dexbacked/DexReader:readByte	()I
/*     */     //   178: i2b
/*     */     //   179: invokespecial 53	org/jf/dexlib2/immutable/value/ImmutableByteEncodedValue:<init>	(B)V
/*     */     //   182: areturn
/*     */     //   183: iload 4
/*     */     //   185: iconst_1
/*     */     //   186: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   189: new 26	org/jf/dexlib2/immutable/value/ImmutableShortEncodedValue
/*     */     //   192: dup
/*     */     //   193: aload_0
/*     */     //   194: iload 4
/*     */     //   196: iconst_1
/*     */     //   197: iadd
/*     */     //   198: invokevirtual 36	org/jf/dexlib2/dexbacked/DexReader:readSizedInt	(I)I
/*     */     //   201: i2s
/*     */     //   202: invokespecial 59	org/jf/dexlib2/immutable/value/ImmutableShortEncodedValue:<init>	(S)V
/*     */     //   205: areturn
/*     */     //   206: iload 4
/*     */     //   208: iconst_1
/*     */     //   209: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   212: new 20	org/jf/dexlib2/immutable/value/ImmutableCharEncodedValue
/*     */     //   215: dup
/*     */     //   216: aload_0
/*     */     //   217: iload 4
/*     */     //   219: iconst_1
/*     */     //   220: iadd
/*     */     //   221: invokevirtual 40	org/jf/dexlib2/dexbacked/DexReader:readSizedSmallUint	(I)I
/*     */     //   224: i2c
/*     */     //   225: invokespecial 54	org/jf/dexlib2/immutable/value/ImmutableCharEncodedValue:<init>	(C)V
/*     */     //   228: areturn
/*     */     //   229: iload 4
/*     */     //   231: iconst_3
/*     */     //   232: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   235: new 23	org/jf/dexlib2/immutable/value/ImmutableIntEncodedValue
/*     */     //   238: dup
/*     */     //   239: aload_0
/*     */     //   240: iload 4
/*     */     //   242: iconst_1
/*     */     //   243: iadd
/*     */     //   244: invokevirtual 36	org/jf/dexlib2/dexbacked/DexReader:readSizedInt	(I)I
/*     */     //   247: invokespecial 57	org/jf/dexlib2/immutable/value/ImmutableIntEncodedValue:<init>	(I)V
/*     */     //   250: areturn
/*     */     //   251: iload 4
/*     */     //   253: bipush 7
/*     */     //   255: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   258: new 24	org/jf/dexlib2/immutable/value/ImmutableLongEncodedValue
/*     */     //   261: dup
/*     */     //   262: aload_0
/*     */     //   263: iload 4
/*     */     //   265: iconst_1
/*     */     //   266: iadd
/*     */     //   267: invokevirtual 37	org/jf/dexlib2/dexbacked/DexReader:readSizedLong	(I)J
/*     */     //   270: invokespecial 58	org/jf/dexlib2/immutable/value/ImmutableLongEncodedValue:<init>	(J)V
/*     */     //   273: areturn
/*     */     //   274: iload 4
/*     */     //   276: iconst_3
/*     */     //   277: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   280: new 22	org/jf/dexlib2/immutable/value/ImmutableFloatEncodedValue
/*     */     //   283: dup
/*     */     //   284: aload_0
/*     */     //   285: iload 4
/*     */     //   287: iconst_1
/*     */     //   288: iadd
/*     */     //   289: invokevirtual 38	org/jf/dexlib2/dexbacked/DexReader:readSizedRightExtendedInt	(I)I
/*     */     //   292: invokestatic 31	java/lang/Float:intBitsToFloat	(I)F
/*     */     //   295: invokespecial 56	org/jf/dexlib2/immutable/value/ImmutableFloatEncodedValue:<init>	(F)V
/*     */     //   298: areturn
/*     */     //   299: iload 4
/*     */     //   301: bipush 7
/*     */     //   303: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   306: new 21	org/jf/dexlib2/immutable/value/ImmutableDoubleEncodedValue
/*     */     //   309: dup
/*     */     //   310: aload_0
/*     */     //   311: iload 4
/*     */     //   313: iconst_1
/*     */     //   314: iadd
/*     */     //   315: invokevirtual 39	org/jf/dexlib2/dexbacked/DexReader:readSizedRightExtendedLong	(I)J
/*     */     //   318: invokestatic 30	java/lang/Double:longBitsToDouble	(J)D
/*     */     //   321: invokespecial 55	org/jf/dexlib2/immutable/value/ImmutableDoubleEncodedValue:<init>	(D)V
/*     */     //   324: areturn
/*     */     //   325: iload 4
/*     */     //   327: iconst_3
/*     */     //   328: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   331: new 16	org/jf/dexlib2/dexbacked/value/DexBackedStringEncodedValue
/*     */     //   334: dup
/*     */     //   335: aload_0
/*     */     //   336: iload 4
/*     */     //   338: invokespecial 50	org/jf/dexlib2/dexbacked/value/DexBackedStringEncodedValue:<init>	(Lorg/jf/dexlib2/dexbacked/DexReader;I)V
/*     */     //   341: areturn
/*     */     //   342: iload 4
/*     */     //   344: iconst_3
/*     */     //   345: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   348: new 17	org/jf/dexlib2/dexbacked/value/DexBackedTypeEncodedValue
/*     */     //   351: dup
/*     */     //   352: aload_0
/*     */     //   353: iload 4
/*     */     //   355: invokespecial 51	org/jf/dexlib2/dexbacked/value/DexBackedTypeEncodedValue:<init>	(Lorg/jf/dexlib2/dexbacked/DexReader;I)V
/*     */     //   358: areturn
/*     */     //   359: iload 4
/*     */     //   361: iconst_3
/*     */     //   362: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   365: new 14	org/jf/dexlib2/dexbacked/value/DexBackedFieldEncodedValue
/*     */     //   368: dup
/*     */     //   369: aload_0
/*     */     //   370: iload 4
/*     */     //   372: invokespecial 48	org/jf/dexlib2/dexbacked/value/DexBackedFieldEncodedValue:<init>	(Lorg/jf/dexlib2/dexbacked/DexReader;I)V
/*     */     //   375: areturn
/*     */     //   376: iload 4
/*     */     //   378: iconst_3
/*     */     //   379: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   382: new 15	org/jf/dexlib2/dexbacked/value/DexBackedMethodEncodedValue
/*     */     //   385: dup
/*     */     //   386: aload_0
/*     */     //   387: iload 4
/*     */     //   389: invokespecial 49	org/jf/dexlib2/dexbacked/value/DexBackedMethodEncodedValue:<init>	(Lorg/jf/dexlib2/dexbacked/DexReader;I)V
/*     */     //   392: areturn
/*     */     //   393: iload 4
/*     */     //   395: iconst_3
/*     */     //   396: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   399: new 13	org/jf/dexlib2/dexbacked/value/DexBackedEnumEncodedValue
/*     */     //   402: dup
/*     */     //   403: aload_0
/*     */     //   404: iload 4
/*     */     //   406: invokespecial 47	org/jf/dexlib2/dexbacked/value/DexBackedEnumEncodedValue:<init>	(Lorg/jf/dexlib2/dexbacked/DexReader;I)V
/*     */     //   409: areturn
/*     */     //   410: iload 4
/*     */     //   412: iconst_0
/*     */     //   413: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   416: new 11	org/jf/dexlib2/dexbacked/value/DexBackedArrayEncodedValue
/*     */     //   419: dup
/*     */     //   420: aload_0
/*     */     //   421: invokespecial 45	org/jf/dexlib2/dexbacked/value/DexBackedArrayEncodedValue:<init>	(Lorg/jf/dexlib2/dexbacked/DexReader;)V
/*     */     //   424: areturn
/*     */     //   425: iload 4
/*     */     //   427: iconst_0
/*     */     //   428: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   431: new 10	org/jf/dexlib2/dexbacked/value/DexBackedAnnotationEncodedValue
/*     */     //   434: dup
/*     */     //   435: aload_0
/*     */     //   436: invokespecial 43	org/jf/dexlib2/dexbacked/value/DexBackedAnnotationEncodedValue:<init>	(Lorg/jf/dexlib2/dexbacked/DexReader;)V
/*     */     //   439: areturn
/*     */     //   440: iload 4
/*     */     //   442: iconst_0
/*     */     //   443: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   446: getstatic 29	org/jf/dexlib2/immutable/value/ImmutableNullEncodedValue:INSTANCE	Lorg/jf/dexlib2/immutable/value/ImmutableNullEncodedValue;
/*     */     //   449: areturn
/*     */     //   450: iload 4
/*     */     //   452: iconst_1
/*     */     //   453: invokestatic 60	org/jf/dexlib2/util/Preconditions:checkValueArg	(II)V
/*     */     //   456: iload 4
/*     */     //   458: iconst_1
/*     */     //   459: if_icmpne +7 -> 466
/*     */     //   462: iconst_1
/*     */     //   463: goto +4 -> 467
/*     */     //   466: iconst_0
/*     */     //   467: invokestatic 52	org/jf/dexlib2/immutable/value/ImmutableBooleanEncodedValue:forBoolean	(Z)Lorg/jf/dexlib2/immutable/value/ImmutableBooleanEncodedValue;
/*     */     //   470: areturn
/*     */     //   471: new 28	org/jf/util/ExceptionWithContext
/*     */     //   474: dup
/*     */     //   475: ldc 3
/*     */     //   477: iconst_1
/*     */     //   478: anewarray 8	java/lang/Object
/*     */     //   481: dup
/*     */     //   482: iconst_0
/*     */     //   483: iload_3
/*     */     //   484: invokestatic 32	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*     */     //   487: aastore
/*     */     //   488: invokespecial 61	org/jf/util/ExceptionWithContext:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   491: athrow
/*     */     //   492: astore_2
/*     */     //   493: aload_2
/*     */     //   494: ldc 1
/*     */     //   496: iconst_1
/*     */     //   497: anewarray 8	java/lang/Object
/*     */     //   500: dup
/*     */     //   501: iconst_0
/*     */     //   502: iload_1
/*     */     //   503: invokestatic 32	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*     */     //   506: aastore
/*     */     //   507: invokestatic 62	org/jf/util/ExceptionWithContext:withContext	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)Lorg/jf/util/ExceptionWithContext;
/*     */     //   510: athrow
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	182	492	java/lang/Exception
/*     */     //   183	205	492	java/lang/Exception
/*     */     //   206	228	492	java/lang/Exception
/*     */     //   229	250	492	java/lang/Exception
/*     */     //   251	273	492	java/lang/Exception
/*     */     //   274	298	492	java/lang/Exception
/*     */     //   299	324	492	java/lang/Exception
/*     */     //   325	341	492	java/lang/Exception
/*     */     //   342	358	492	java/lang/Exception
/*     */     //   359	375	492	java/lang/Exception
/*     */     //   376	392	492	java/lang/Exception
/*     */     //   393	409	492	java/lang/Exception
/*     */     //   410	424	492	java/lang/Exception
/*     */     //   425	439	492	java/lang/Exception
/*     */     //   440	449	492	java/lang/Exception
/*     */     //   450	470	492	java/lang/Exception
/*     */     //   471	492	492	java/lang/Exception
/*     */   }
/*     */ 
/*     */   public static void skipFrom(DexReader reader)
/*     */   {
/* 113 */     int startOffset = reader.getOffset();
/*     */     try
/*     */     {
/* 116 */       int b = reader.readUbyte();
/* 117 */       int valueType = b & 0x1F;
/*     */ 
/* 119 */       switch (valueType) {
/*     */       case 0:
/* 121 */         reader.skipByte();
/* 122 */         break;
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 6:
/*     */       case 16:
/*     */       case 17:
/*     */       case 23:
/*     */       case 24:
/*     */       case 25:
/*     */       case 26:
/*     */       case 27:
/* 134 */         int valueArg = b >>> 5;
/* 135 */         reader.moveRelative(valueArg + 1);
/* 136 */         break;
/*     */       case 28:
/* 138 */         DexBackedArrayEncodedValue.skipFrom(reader);
/* 139 */         break;
/*     */       case 29:
/* 141 */         DexBackedAnnotationEncodedValue.skipFrom(reader);
/* 142 */         break;
/*     */       case 30:
/*     */       case 31:
/* 145 */         break;
/*     */       case 1:
/*     */       case 5:
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/*     */       case 14:
/*     */       case 15:
/*     */       case 18:
/*     */       case 19:
/*     */       case 20:
/*     */       case 21:
/*     */       case 22:
/*     */       default:
/* 147 */         throw new ExceptionWithContext("Invalid encoded_value type: 0x%x", new Object[] { Integer.valueOf(valueType) });
/*     */       }
/*     */     } catch (Exception ex) {
/* 150 */       throw ExceptionWithContext.withContext(ex, "Error while skipping encoded value at offset 0x%x", new Object[] { Integer.valueOf(startOffset) });
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.value.DexBackedEncodedValue
 * JD-Core Version:    0.6.0
 */