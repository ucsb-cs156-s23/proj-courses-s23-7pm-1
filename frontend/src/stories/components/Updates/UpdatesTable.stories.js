import React from 'react';

import UpdatesTable from "main/components/Updates/UpdatesTable";
import { updatesFixtures } from 'fixtures/updatesFixtures';

export default {
    title: 'components/Updates/UpdatesTable',
    component: UpdatesTable
};

const Template = (args) => {
    return (
        <UpdatesTable {...args} />
    )
};

export const Empty = Template.bind({});

Empty.args = {
    subjects: []
};

export const ThreeUpdates = Template.bind({});

ThreeUpdates.args = {
    subjects: updatesFixtures.threeUpdates
};


