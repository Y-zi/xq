#ifdef GL_ES 
	precision mediump float;
#endif

uniform sampler2D 	u_texture;
varying vec2 		v_texCoord;
varying vec4 		v_fragmentColor;

void main(void)                                            
{            
	float alpha = texture2D(u_texture, v_texCoord).a;              
    float color = dot(texture2D(u_texture, v_texCoord).rgb, vec3(0.299, 0.587, 0.114)); 
	
	gl_FragColor = vec4(color, color, color, alpha);
}


     