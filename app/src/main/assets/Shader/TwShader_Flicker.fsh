#ifdef GL_ES 
	precision mediump float;
#endif 
 
uniform sampler2D u_texture; 
varying vec2 v_texCoord;  
varying vec4 v_fragmentColor; 

uniform vec2 blurSize;
uniform vec4 substract;
uniform float time;

void main(void) 
{
	float alpha    = texture2D(u_texture, v_texCoord).a;
	vec3  srcColor = texture2D(u_texture, v_texCoord).rgb;
	vec3  dstColor = srcColor + srcColor * time;

	gl_FragColor = vec4(dstColor.r, dstColor.g, dstColor.b, alpha); 
}
