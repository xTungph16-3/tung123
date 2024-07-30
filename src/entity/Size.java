/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Trong Phu
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class Size {

    private Integer size_id;
    
    private Integer giatri;

    public Size(Integer giatri) {
        this.giatri = giatri;
    }
    
    public Object[] DataSize(){
        return new Object[]{
            size_id,
            giatri
        };
    }
}
