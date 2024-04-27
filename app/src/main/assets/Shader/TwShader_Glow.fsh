
const int 			g_iFilterTime = 9; 	 // 过滤次数 
const float 		g_fGene = (1.0/(1.0 + 2.0*(0.93 + 0.8 + 0.7 + 0.6 + 0.5 + 0.4 + 0.3 + 0.2 + 0.1))); // 衰减因子
uniform sampler2D 	g_Decal;
uniform bool 	  	g_bFiterMode;
uniform float 	  	g_fGlowGene;
uniform vec2 		g_vec2HorizontalDir; // 水平过滤方向 
uniform vec2 		g_vec2VerticalDir;   // 竖直过滤方向 
uniform float 		g_fFilterOffset;     // 过滤偏移

void main()
{
	float aryAttenuation[g_iFilterTime];
	aryAttenuation[0] = 0.93;
	aryAttenuation[1] = 0.8;
	aryAttenuation[2] = 0.7;
	aryAttenuation[3] = 0.6;
	aryAttenuation[4] = 0.5;
	aryAttenuation[5] = 0.4;
	aryAttenuation[6] = 0.3;
	aryAttenuation[7] = 0.2;
	aryAttenuation[8] = 0.1;

	// 采样原始颜色 
	vec2 vec2Tex0 = gl_TexCoord[0].st;
	vec4 vec4Color = texture2D(g_Decal, vec2Tex0) * g_fGene;

	// 计算过滤方向 
	vec2 vec2FilterDir = g_vec2HorizontalDir + vec2(g_fFilterOffset, 0.0); // 水平过滤 
	if (!g_bFiterMode)
	{
		vec2FilterDir = g_vec2VerticalDir + vec2(0.0, g_fFilterOffset); // 竖直过滤 
	}

	// 进行过滤 
	vec2 vec2Step = vec2FilterDir;
	for(int i = 0; i< g_iFilterTime; ++i)
	{
		vec4Color += texture2D(g_Decal, vec2Tex0 + vec2Step)*aryAttenuation[i]*g_fGene;
		vec4Color += texture2D(g_Decal, vec2Tex0 - vec2Step)*aryAttenuation[i]*g_fGene;
		vec2Step += vec2FilterDir;
	}
	
	if (g_bFiterMode)
	{
		gl_FragColor = vec4Color*g_fGlowGene;
	}
	else
	{
		gl_FragColor = vec4Color;
	}
 }

//混合着色器:
uniform sampler2D g_Decal;
void main()
{
	gl_FragColor = texture2D(g_Decal, gl_TexCoord[0].st);
}
