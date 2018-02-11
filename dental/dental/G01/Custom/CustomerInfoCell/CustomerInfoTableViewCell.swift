//
//  CustomerInfoTableViewCell.swift
//  dental
//
//  Created by Lâm Phạm on 1/11/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit

class CustomerInfoTableViewCell: UITableViewCell {

    
    @IBOutlet weak var objectView: CommonObjectView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    func loadData(obj: MasterObject) {
        objectView.loadObject(object: obj)
    }
    
}
