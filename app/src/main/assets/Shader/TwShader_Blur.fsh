#ifdef GL_ES														
precision mediump float;											
#endif																
																	
varying vec4 v_fragmentColor;										
varying vec2 v_texCoord;											
																	
uniform sampler2D u_texture;										
																	
uniform vec2 blurSize;												
uniform vec4 substract;												
																	
void main()															
{																	
	vec4 sum = vec4(0.0);
	sum += texture2D(u_texture, v_texCoord - 2.0 * blurSize) * 0.06;
	sum += texture2D(u_texture, v_texCoord - 1.5 * blurSize) * 0.08;
	sum += texture2D(u_texture, v_texCoord - 1.0 * blurSize) * 0.12;
	sum += texture2D(u_texture, v_texCoord                 ) * 0.2;
	sum += texture2D(u_texture, v_texCoord + 1.0 * blurSize) * 0.12;
	sum += texture2D(u_texture, v_texCoord + 1.5 * blurSize) * 0.08;
	sum += texture2D(u_texture, v_texCoord + 2.0 * blurSize) * 0.06;

	gl_FragColor = vec4(1, 1, 0, sum.a * 1.5);
}